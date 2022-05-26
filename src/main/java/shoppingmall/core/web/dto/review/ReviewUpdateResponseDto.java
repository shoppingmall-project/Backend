package shoppingmall.core.web.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateResponseDto {

    private Long id;

    public ReviewUpdateResponseDto(Long id) {
        this.id = id;

    }
}
