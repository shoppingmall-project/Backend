package shoppingmall.core.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.comment.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentFindResponseDto {

    private Long boardId;
    private Long commentId;
    private String author;
    private String content;
    private String imageUrl;
    private LocalDateTime createdDate;

    @Builder
    public CommentFindResponseDto(Long boardId, Long commentId, String author, String content, String imageUrl, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
    }

    public static CommentFindResponseDto toResponseDto(Comment entity) {
        return CommentFindResponseDto.builder()
                .boardId(entity.getBoard().getId())
                .commentId(entity.getId())
                .author(entity.getAuthor())
                .content(entity.getContent())
                .imageUrl(entity.getImageUrl())
                .createdDate(entity.getCreatedDate())
                .build();
    }

}
