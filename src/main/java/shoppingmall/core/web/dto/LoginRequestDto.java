package shoppingmall.core.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotEmpty
    private String account;
    @NotEmpty
    private String password;

    @Builder
    public LoginRequestDto(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
