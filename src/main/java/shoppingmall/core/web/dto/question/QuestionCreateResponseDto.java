package shoppingmall.core.web.dto.question;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class QuestionCreateResponseDto {

    private Long id;

    @Builder
    public QuestionCreateResponseDto(Long id) {
        this.id = id;
    }
}
