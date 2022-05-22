package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
public class GoodsCreateRequestDto {

    private String category;
    private String name;
    private String price;
    private String stock;
    private String description;
    private String brand;
    private String country;

    public GoodsCreateRequestDto(String category, String name, String price, String stock, String description, String brand, String country) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }

    @Builder


    public Goods toEntity() {
        return Goods.builder()
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .build();

    }
}
