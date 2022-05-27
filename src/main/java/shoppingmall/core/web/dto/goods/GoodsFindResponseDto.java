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
    private int price;
    private int stock;
    private String description;
    private String brand;
    private String country;
    private String imageUrl;

    @Builder
    public GoodsFindResponseDto(Long id, String category, String name, int price, int stock, String description, String brand, String country, String imageUrl) {
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
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
