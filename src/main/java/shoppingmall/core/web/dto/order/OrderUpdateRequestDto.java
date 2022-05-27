package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;

@Getter
@NoArgsConstructor
public class OrderUpdateRequestDto {
    private Long goods_id;

    private String request;

    private int payment;

    @Builder
    public OrderUpdateRequestDto(Long goods_id, String request, int payment) {
        this.goods_id = goods_id;
        this.request = request;
        this.payment = payment;
    }
}
