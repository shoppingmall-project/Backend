package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodsCreateResponseDto {

    private Long goodsId;

    @Builder
    public GoodsCreateResponseDto(Long goodsId) {
        this.goodsId = goodsId;
    }
}
