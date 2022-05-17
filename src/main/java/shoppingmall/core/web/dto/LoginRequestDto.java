package shoppingmall.core.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotEmpty
    private String account;
    @NotEmpty
    private String password;

}
