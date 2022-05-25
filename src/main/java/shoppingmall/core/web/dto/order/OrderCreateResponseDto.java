package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponseDto {

    private Long id;

    @Builder
    public OrderCreateResponseDto(Long id) {
        this.id = id;
    }
}
