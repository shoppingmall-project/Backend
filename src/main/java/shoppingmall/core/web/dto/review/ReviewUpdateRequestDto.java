package shoppingmall.core.web.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private String content;

    private String imageUrl;

    @Builder
    public ReviewUpdateRequestDto(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }


}
