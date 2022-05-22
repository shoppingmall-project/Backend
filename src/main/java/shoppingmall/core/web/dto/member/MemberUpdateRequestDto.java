package shoppingmall.core.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    private String password;
    private String role;
    private String email;

    @Builder
    public MemberUpdateRequestDto(String password, String role, String email, String gender) {
        this.password = password;
        this.role = role;
        this.email = email;
    }
}
