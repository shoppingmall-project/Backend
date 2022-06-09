package shoppingmall.core.web.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class AnswerUpdateResponseDto {

    private Long id;

    @Builder
    public AnswerUpdateResponseDto(Long id) {
        this.id = id;
    }
}
