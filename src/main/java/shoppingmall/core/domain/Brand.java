package shoppingmall.core.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //브랜드는 제품 종류에 귀속되는게 맞는것 같음.
    @Column(length = 30, nullable = false)
    private String name;

    @ManyToOne
    private Product product;

}
