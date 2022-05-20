package shoppingmall.core.domain.Goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Country;
import shoppingmall.core.domain.Product;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Wine , Beer 같은 것들이 들어가는 테이블
    @OneToOne
    @JoinColumn(name = "product_name")
    private Product product;

    @OneToOne
    @JoinColumn(name = "country_name")
    private Country country;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String price;

    @Column(length = 30, nullable = false)
    private String vintage;

    @Column(length = 30, nullable = false)
    private String rating;

    @Builder
    public Goods(Product product, Country country, String name, String price, String vintage, String rating) {
        this.product = product;
        this.country = country;
        this.name = name;
        this.price = price;
        this.vintage = vintage;
        this.rating = rating;
    }
}
