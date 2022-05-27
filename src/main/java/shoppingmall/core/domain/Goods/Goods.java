package shoppingmall.core.domain.Goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
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
    private int price;

    @Column(length = 30, nullable = false)
    private int stock;

    @Column(length = 30, nullable = false)
    private String description;

    @Column(length = 30)
    private String brand;

    @Column(length = 30)
    private String country;

    @Column(length = 200)
    private String imageUrl;

    @Builder
    public Goods(Long id, String category, String name, int price, int stock, String description, String brand, String country, String imageUrl) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
        this.imageUrl = imageUrl;
    }

    public void updateGoods(String category, String name, int price, int stock, String description, String brand, String country) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }

    public void updateUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
