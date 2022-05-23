package shoppingmall.core.web.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateResponseDto {

    private Long id;

    public CommentCreateResponseDto(Long id) {
        this.id = id;
    }
}
