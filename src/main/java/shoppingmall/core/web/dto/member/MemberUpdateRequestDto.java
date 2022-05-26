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
    private String address;
    private String phoneNum;

    @Builder
    public MemberUpdateRequestDto(String password, String role, String email, String gender, String address, String phoneNum) {
        this.password = password;
        this.role = role;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }
}
