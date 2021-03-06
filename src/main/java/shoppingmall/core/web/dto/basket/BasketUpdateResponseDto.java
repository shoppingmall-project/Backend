package shoppingmall.core.web.dto.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketUpdateResponseDto {

    private Long id;

    @Builder
    public BasketUpdateResponseDto(Long id) {
        this.id = id;

    }
}