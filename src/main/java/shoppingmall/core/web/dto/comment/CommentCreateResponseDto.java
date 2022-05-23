package shoppingmall.core.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateResponseDto {

    private Long id;

    @Builder
    public CommentCreateResponseDto(Long id) {
        this.id = id;

    }
}