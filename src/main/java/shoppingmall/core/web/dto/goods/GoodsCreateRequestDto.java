package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Product;

@Getter
@NoArgsConstructor
public class GoodsCreateRequestDto {

    private Product product;
    private Country country;
    private String name;
    private String price;
    private String vintage;
    private String rating;

    @Builder
    public GoodsCreateRequestDto(Product product, Country country, String name, String price, String vintage, String rating) {
        this.product = product;
        this.country = country;
        this.name = name;
        this.price = price;
        this.vintage = vintage;
        this.rating = rating;
    }

    public Goods toEntity() {
        return Goods.builder()
                .product(product)
                .country(country)
                .name(name)
                .price(price)
                .vintage(vintage)
                .rating(rating)
                .build();

    }
}
