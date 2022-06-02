package shoppingmall.core.web.dto.answer;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class AnswerUpdateResponseDto {

    private Long id;

    @Builder
    public AnswerUpdateResponseDto(Long id) {
        this.id = id;
    }
}
