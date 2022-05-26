package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.order.Order;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private String request;
    @NotEmpty
    private int payment; //1: 신용카드 2:네이버페이 3:카카오페이 ...

    @Builder
    public OrderCreateRequestDto(String request, int payment) {
        this.request = request;
        this.payment = payment;
    }
    public Order toEntity() {
        return Order.builder()
                .request(request)
                .payment(payment)
                .build();
    }
}
