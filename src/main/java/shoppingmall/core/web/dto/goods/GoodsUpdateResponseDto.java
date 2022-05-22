package shoppingmall.core.web.dto.goods;

import lombok.Builder;

public class GoodsUpdateResponseDto {

    Long id;

    @Builder
    public GoodsUpdateResponseDto(Long id) {
        this.id = id;
    }

}
