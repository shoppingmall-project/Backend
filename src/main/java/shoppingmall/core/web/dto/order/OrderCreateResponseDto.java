package shoppingmall.core.web.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponseDto {

    private Long id;

    public OrderCreateResponseDto(Long id) {
        this.id = id;
    }
}
