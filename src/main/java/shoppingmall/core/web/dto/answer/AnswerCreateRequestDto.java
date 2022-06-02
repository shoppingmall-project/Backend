package shoppingmall.core.web.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.QandA.answer.Answer;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AnswerCreateRequestDto {

    @NotEmpty
    private String content;


    @Builder
    public AnswerCreateRequestDto(String content) {
        this.content = content;
    }

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .build();
    }
}
