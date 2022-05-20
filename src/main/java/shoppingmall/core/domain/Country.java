package shoppingmall.core.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;
}
