package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.order.Order;

@Getter
@NoArgsConstructor
public class OrderFindResponseDto {

    private Long id;
    private Long memberId;
    private String memberName;
    private Long goodsId;
    private String goodsName;
    private String request;
    private int payment; //1: 신용카드 2:네이버페이 3:카카오페이 ...

    @Builder
    public OrderFindResponseDto(Long id, Long memberId, String memberName, Long goodsId, String goodsName, String request, int payment) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.request = request;
        this.payment = payment;
    }

    public static OrderFindResponseDto toResponseDto(Order entity) {
        return OrderFindResponseDto.builder()
                .id(entity.getId())
                .memberId(entity.getMember().getId())
                .memberName(entity.getMember().getName())
                .goodsId(entity.getGoods().getId())
                .goodsName(entity.getGoods().getName())
                .request(entity.getRequest())
                .payment(entity.getPayment())
                .build();
    }

}
