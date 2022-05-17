package shoppingmall.core.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.core.service.UserService;

@CrossOrigin(origins = "localhost:8080")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/user")
    public UserResponseDto findUser() {
        return userService.findUser();
    }
}
