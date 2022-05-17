package shoppingmall.core.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String account;

    @Column(length = 30, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private Date birth;

    @Column(length = 30, nullable = false)
    private String gender;

    @Column(length = 30, nullable = false)
    private LocalDateTime created_dt;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String name;

    @Builder
    public User(Long id, String account, String password, Date birth, String gender, LocalDateTime created_dt, String email, String name) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.birth = birth;
        this.gender = gender;
        this.created_dt = created_dt;
        this.email = email;
        this.name = name;
    }
}
