package shoppingmall.core.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.domain.comment.Comment;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {

    private String author;
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
