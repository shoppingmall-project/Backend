package shoppingmall.core.web.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateResponseDto {

    private Long id;

    public ReviewCreateResponseDto(Long id) {
        this.id = id;

    }
}