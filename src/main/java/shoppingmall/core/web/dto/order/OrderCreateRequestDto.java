package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.order.Order;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    GoodsRepository goodsRepository;
    private Long goods_id;
    private String request;
    @NotEmpty
    private int count;
    @NotEmpty
    private int payment; //1: 신용카드 2:네이버페이 3:카카오페이 ...

    @Builder
    public OrderCreateRequestDto(Long goods_id, String request, int count, int payment) {
        this.goods_id = goods_id;
        this.request = request;
        this.count = count;
        this.payment = payment;
    }
    public Order toEntity() {
        return Order.builder()
                .request(request)
                .count(count)
                .payment(payment)
                .build();
    }

}
