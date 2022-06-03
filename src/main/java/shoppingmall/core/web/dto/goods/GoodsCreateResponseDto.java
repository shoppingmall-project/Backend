package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodsCreateResponseDto {

    private Long id;

    public GoodsCreateResponseDto(Long id) {
        this.id = id;
    }
}
