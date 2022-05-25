package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketCreateResponseDto {

    private Long id;

    @Builder
    public BasketCreateResponseDto(Long id) {
        this.id = id;
    }
}
