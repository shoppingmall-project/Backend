package shoppingmall.core.web.dto.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.basket.Basket;

@Getter
@NoArgsConstructor
public class BasketFindResponseDto {

    private Long id;
    private Long memberId;
    private Long goodsId;
    private String goodsName;
    private int count;

    @Builder
    public BasketFindResponseDto(Long id, Long memberId, Long goodsId, String goodsName, int count) {
        this.id = id;
        this.memberId = memberId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.count = count;
    }

    public static BasketFindResponseDto toResponseDto(Basket entity) {
        return BasketFindResponseDto.builder()
                .id(entity.getId())
                .memberId(entity.getMember().getId())
                .goodsId(entity.getGoods().getId())
                .goodsName(entity.getGoods().getName())
                .count(entity.getCount())
                .build();
    }

}
