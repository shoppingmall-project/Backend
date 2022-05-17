package shoppingmall.core.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.User;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String account;
    private String password;
    private String gender;
    private String email;
    private String name;
    private String role;


    public UserResponseDto(User entity) {
        this.account = entity.getAccount();
        this.password = entity.getPassword();
        this.gender = entity.getGender();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.role = entity.getRole();
    }
}
//public class UserResponseDto {
//
//    private String account;
//    private String password;
//    private Date birth;
//    private String gender;
//    private LocalDateTime created_dt;
//    private String email;
//    private String name;
//    private String role
//
//
//    public UserResponseDto(User entity) {
//        this.account = entity.getAccount();
//        this.password = entity.getPassword();
//        this.birth = entity.getBirth();
//        this.gender = entity.getGender();
//        this.created_dt = entity.getCreated_dt();
//        this.email = entity.getEmail();
//        this.name = entity.getName();
//        this.role = entity.getRole();
//    }
//}
