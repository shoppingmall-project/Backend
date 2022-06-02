package shoppingmall.core.web.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerCreateResponseDto {

    private Long id;

    @Builder
    public AnswerCreateResponseDto(Long id) {
        this.id = id;
    }
}
