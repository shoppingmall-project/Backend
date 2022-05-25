package shoppingmall.core.web.dto.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.basket.Basket;

@Getter
@NoArgsConstructor
public class BasketCreateRequestDto {

    private int count;

    @Builder
    public BasketCreateRequestDto( int count) {
        this.count = count;
    }

    public Basket toEntity() {
        return Basket.builder()
                .count(count)
                .build();
    }
}
