package shoppingmall.core.web.dto.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.basket.Basket;

@Getter
@NoArgsConstructor
public class BasketCreateRequestDto {
    private Long goods_id;
    private int count;

    @Builder
    public BasketCreateRequestDto(Long goods_id, int count) {
        this.goods_id = goods_id;
        this.count = count;
    }

    public Basket toEntity() {
        return Basket.builder()
                .count(count)
                .build();
    }
}
