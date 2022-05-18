package shoppingmall.core.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
public class Product {

//    id 1 , wine, 브랜드 A
//    id 2 , wine, 브랜드 B
//    id 3 , beer, 브랜드 ~~
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    //브랜드는 제품 종류에 귀속되는게 맞는것 같음.
}
