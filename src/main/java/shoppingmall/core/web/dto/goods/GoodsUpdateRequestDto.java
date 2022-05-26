package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodsUpdateRequestDto {


    private String category;
    private String name;
    private int price;
    private int stock;
    private String description;
    private String brand;
    private String country;

    @Builder
    public GoodsUpdateRequestDto(String category, String name, int price, int stock, String description, String brand, String country) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }
}
