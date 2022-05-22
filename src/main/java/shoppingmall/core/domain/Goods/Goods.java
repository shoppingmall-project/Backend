package shoppingmall.core.domain.Goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String category;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String price;

    @Column(length = 30, nullable = false)
    private String stock;

    @Column(length = 30, nullable = false)
    private String description;

    @Column(length = 30)
    private String brand;

    @Column(length = 30)
    private String country;

    @Builder
    public Goods(Long id, String category, String name, String price, String stock, String description, String brand, String country) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }

    public void updateGoods(String category, String name, String price, String stock, String description, String brand, String country) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }
}
