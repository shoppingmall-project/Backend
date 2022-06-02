package shoppingmall.core.web.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionUpdateResponseDto {

    private Long id;

    @Builder
    public QuestionUpdateResponseDto(Long id) {
        this.id = id;
    }
}
