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
    private Long memberId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdDate;
    private String writer;

    @Builder
    public ReviewFindResponseDto(Long goodsId, Long commentId, Long memberId, String content, String imageUrl, LocalDateTime createdDate, String writer) {
        this.goodsId = goodsId;
        this.commentId = commentId;
        this.memberId = memberId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.writer = writer;
    }

    public static ReviewFindResponseDto toResponseDto(Review entity) {
        return ReviewFindResponseDto.builder()
                .goodsId(entity.getGoods().getId())
                .commentId(entity.getId())
                .memberId(entity.getMember().getId())
                .writer(entity.getMember().getName())
                .content(entity.getContent())
                .imageUrl(entity.getImageUrl())
                .createdDate(entity.getCreatedDate())
                .build();
    }
    
}
