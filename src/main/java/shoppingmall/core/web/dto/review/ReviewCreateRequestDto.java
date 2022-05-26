package shoppingmall.core.web.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shoppingmall.core.domain.review.Review;

@Setter
@Getter
@NoArgsConstructor
public class ReviewCreateRequestDto {

    private String author;
    private String content;

    @Builder
    public ReviewCreateRequestDto(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Review toEntity() {
        return Review.builder()
                .author(author)
                .content(content)
                .build();
    }
}
