package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class GoodsCreateRequestDto {

    @NotEmpty
    private String category;
    @NotEmpty
    private String name;
    @NotEmpty
    private int price;
    @NotEmpty
    private int stock;
    @NotEmpty
    private String description;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String country;

    @Builder
    public GoodsCreateRequestDto(String category, String name, int price, int stock, String description, String brand, String country) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }


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
