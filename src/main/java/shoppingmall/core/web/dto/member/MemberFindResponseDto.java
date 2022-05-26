package shoppingmall.core.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.member.Member;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberFindResponseDto {

    private Long id;
    private String account;
    private String name;
    private String password;
    private String role;
    @Email
    private String email;
    private String gender;
    private LocalDateTime createdDate;
    private String address;
    private String phoneNum;

    @Builder
    public MemberFindResponseDto(Long id, String account, String name, String password, String role, String email, String gender, LocalDateTime createdDate, String address, String phoneNum) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
        this.gender = gender;
        this.createdDate = createdDate;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public static MemberFindResponseDto toResponseDto(Member entity) {
        return MemberFindResponseDto.builder()
                .id(entity.getId())
                .account(entity.getAccount())
                .name(entity.getName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .createdDate(entity.getCreatedDate())
                .address(entity.getAddress())
                .phoneNum(entity.getPhoneNum())
                .build();
    }
}
