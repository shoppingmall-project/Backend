package shoppingmall.core.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateResponseDto {

    private Long id;

    @Builder
    public CommentUpdateResponseDto(Long id) {
        this.id = id;

    }
}
