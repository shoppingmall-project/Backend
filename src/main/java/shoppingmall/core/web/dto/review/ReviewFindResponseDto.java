package shoppingmall.core.web.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.review.Review;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewFindResponseDto {

    private Long goodsId;
    private Long commentId;
    private String author;
    private String content;
    private String imageUrl;
    private LocalDateTime createdDate;

    @Builder
    public ReviewFindResponseDto(Long goodsId, Long commentId, String author, String content, String imageUrl, LocalDateTime createdDate) {
        this.goodsId = goodsId;
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
    }

    public static ReviewFindResponseDto toResponseDto(Review entity) {
        return ReviewFindResponseDto.builder()
                .goodsId(entity.getGoods().getId())
                .commentId(entity.getId())
                .author(entity.getAuthor())
                .content(entity.getContent())
                .imageUrl(entity.getImageUrl())
                .createdDate(entity.getCreatedDate())
                .build();
    }

}
