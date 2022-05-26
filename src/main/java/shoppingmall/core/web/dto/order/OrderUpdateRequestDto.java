package shoppingmall.core.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateRequestDto {

    private String request;

    private int payment;

    @Builder
    public OrderUpdateRequestDto(String request, int payment) {
        this.request = request;
        this.payment = payment;
    }
}
