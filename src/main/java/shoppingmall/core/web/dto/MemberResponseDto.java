package shoppingmall.core.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Member;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String account;
    private String name;
    private String role;
    private String email;
    private String gender;
    private String password;

    public MemberResponseDto(Member entity) {
        this.account = entity.getAccount();
        this.name = entity.getName();
        this.role = entity.getRole();
        this.email = entity.getEmail();
        this.gender = entity.getGender();
        this.password = entity.getPassword();
    }



}
