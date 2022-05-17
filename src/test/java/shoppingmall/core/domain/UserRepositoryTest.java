package shoppingmall.core.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();

    }

    @Test
    @DisplayName("유저 저장")
    public void saveUser() {
        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        userRepository.save(User.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        Assertions.assertThat(user.getAccount()).isEqualTo(account);
        Assertions.assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("유저 삭제")
    public void deleteUser() {
        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        userRepository.save(User.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build());


        //when
        if (userRepository.findByAccount("test").isPresent()) {
            System.out.println("account = " + account);
            userRepository.deleteByAccount("test");
        } else {
            System.out.println("해당 계정이 없습니다");

        }
        //then
        Assertions.assertThat(userRepository.findByAccount("test")).isEmpty();
    }

}
