package shoppingmall.core.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();

    }

    @Test
    @DisplayName("유저 생성 및 조회")
    public void saveMember() {
        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        if (memberRepository.findByAccount(account).isEmpty()) {
            memberRepository.save(Member.builder()
                    .account(account)
                    .password(password)
                    .gender(gender)
                    .email(email)
                    .name(name)
                    .role(role)
                    .build());
        } else {
            System.out.println("이미 존재하는 아이디입니다.");
        }
        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member member = memberList.get(0);
        Assertions.assertThat(member.getAccount()).isEqualTo(account);
        Assertions.assertThat(member.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("유저 삭제")
    public void deleteMember() {
        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        Member member = memberRepository.save(Member.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build());

        memberRepository.deleteById(member.getId());

        //then
        Assertions.assertThat(memberRepository.findByAccount("test")).isEmpty();
    }
}

