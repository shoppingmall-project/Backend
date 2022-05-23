package shoppingmall.core.web.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private String content;

    private String imageUrl;

    @Builder
    public CommentUpdateRequestDto(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }


}
