package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
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
    private String imageUrl;

    @Builder
    public GoodsUpdateRequestDto(String category, String name, String price, String stock, String description, String brand, String country, String imageUrl) {
        this.category = category;
        this.name = name;
        this.price = Integer.parseInt(price);
        this.stock = Integer.parseInt(stock);
        this.description = description;
        this.brand = brand;
        this.country = country;
        this.imageUrl = imageUrl;

    }
}
