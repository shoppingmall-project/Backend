package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateResponseDto {

    private Long id;

    @Builder
    public OrderUpdateResponseDto(Long id) {
        this.id = id;
    }
}
