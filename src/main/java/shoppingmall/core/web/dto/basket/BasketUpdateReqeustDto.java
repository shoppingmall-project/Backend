package shoppingmall.core.web.dto.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketUpdateReqeustDto {

    int count;

    @Builder
    public BasketUpdateReqeustDto(int count) {
        this.count = count;
    }
}
