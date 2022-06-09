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
    private String memberPhoneNum;
    private String memberAddress;
    private Long goodsId;
    private String goodsName;
    private int goodsPrice;
    private String request;
    private int count;
    private int payment; //1: 신용카드 2:네이버페이 3:카카오페이 ...

    @Builder
    public OrderFindResponseDto(Long id, Long memberId, String memberName, String memberPhoneNum, String memberAddress, Long goodsId, String goodsName, String request, int goodsPrice, int count, int payment) {

        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPhoneNum = memberPhoneNum;
        this.memberAddress = memberAddress;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.request = request;
        this.count = count;
        this.payment = payment;
    }

    public static OrderFindResponseDto toResponseDto(Order entity) {
        return OrderFindResponseDto.builder()
                .id(entity.getId())
                .memberId(entity.getMember().getId())
                .memberName(entity.getMember().getName())
                .memberPhoneNum(entity.getMember().getPhoneNum())
                .memberAddress(entity.getMember().getAddress())
                .goodsId(entity.getGoods().getId())
                .goodsName(entity.getGoods().getName())
                .goodsPrice(entity.getGoods().getPrice())
                .request(entity.getRequest())
                .count(entity.getCount())
                .payment(entity.getPayment())
                .build();
    }
}
