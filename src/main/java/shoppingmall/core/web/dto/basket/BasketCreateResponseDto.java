package shoppingmall.core.web.dto.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketCreateResponseDto {

    private Long id;

    public BasketCreateResponseDto(Long id) {
        this.id = id;
    }
}
