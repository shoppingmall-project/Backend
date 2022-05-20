package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Country;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Product;

@Getter
@NoArgsConstructor
public class GoodsFindResponseDto {

    private Long id;
    private Product product;
    private Country country;
    private String name;
    private String price;
    private String vintage;
    private String rating;

    @Builder
    public GoodsFindResponseDto(Long id, Product product, Country country, String name, String price, String vintage, String rating) {
        this.id = id;
        this.product = product;
        this.country = country;
        this.name = name;
        this.price = price;
        this.vintage = vintage;
        this.rating = rating;
    }

    public static GoodsFindResponseDto toResponseDto(Goods entity) {
        return GoodsFindResponseDto.builder()
                .id(entity.getId())
                .product(entity.getProduct())
                .country(entity.getCountry())
                .name(entity.getName())
                .price(entity.getPrice())
                .vintage(entity.getVintage())
                .rating(entity.getRating())
                .build();
    }


}
