package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;

@Getter
@NoArgsConstructor
public class GoodsFindResponseDto {

    private Long id;
    private String category;
    private String name;
    private String price;
    private String stock;
    private String description;
    private String brand;
    private String country;

    @Builder
    public GoodsFindResponseDto(Long id, String category, String name, String price, String stock, String description, String brand, String country) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.brand = brand;
        this.country = country;
    }

    public static GoodsFindResponseDto toResponseDto(Goods entity) {
        return GoodsFindResponseDto.builder()
                .id(entity.getId())
                .category(entity.getCategory())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .description(entity.getDescription())
                .brand(entity.getBrand())
                .country(entity.getCountry())
                .build();
    }
}