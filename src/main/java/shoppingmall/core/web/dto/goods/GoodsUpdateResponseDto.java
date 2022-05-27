package shoppingmall.core.web.dto.goods;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsUpdateResponseDto {

    Long id;

    @Builder
    public GoodsUpdateResponseDto(Long id) {
        this.id = id;
    }

}
