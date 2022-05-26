package shoppingmall.core.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.member.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class MemberCreateRequestDto {
    @NotEmpty
    private String account;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;
    @Email
    private String email;
    @NotEmpty
    private String gender;
    private String address;
    private String phoneNum;

    @Builder

    public MemberCreateRequestDto(String account, String name, String password, String role, String email, String gender, String address, String phoneNum) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public Member toEntity() {
        return Member.builder()
                .account(account)
                .password(password)
                .name(name)
                .role(role)
                .email(email)
                .gender(gender)
                .address(address)
                .phoneNum(phoneNum)
                .build();
    }
}
