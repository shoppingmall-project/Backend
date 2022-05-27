package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shoppingmall.core.domain.Goods.Goods;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class GoodsCreateRequestDto {

    @NotEmpty
    private String category;
    @NotEmpty
    private String name;
    private int price;
    private int stock;
    @NotEmpty
    private String description;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String country;


    @Builder
    public GoodsCreateRequestDto(String category, String name, String price, String stock, String description, String brand, String country) {
        this.category = category;
        this.name = name;
        this.price = Integer.parseInt(price);
        this.stock = Integer.parseInt(stock);
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
