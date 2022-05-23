package shoppingmall.core.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.comment.Comment;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {

    @NotEmpty
    private String author;
    @NotEmpty
    private String content;

    @Builder
    public CommentCreateRequestDto(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .author(author)
                .content(content)
                .build();
    }
}
