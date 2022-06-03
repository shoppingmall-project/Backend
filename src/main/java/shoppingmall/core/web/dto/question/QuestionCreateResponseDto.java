package shoppingmall.core.web.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QuestionCreateResponseDto {

    private Long id;

    public QuestionCreateResponseDto(Long id) {
        this.id = id;
    }
}
