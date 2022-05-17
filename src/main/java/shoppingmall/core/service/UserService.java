package shoppingmall.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.User;
import shoppingmall.core.domain.UserRepository;
import shoppingmall.core.web.UserResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto findUser() {
        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        return new UserResponseDto(user);
    }
}
