package shoppingmall.core.web.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.QandA.question.Question;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class QuestionCreateRequestDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    @Builder
    public QuestionCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Question toEntity() {
        return Question.builder()
                .title(title)
                .content(content)
                .answerNum(0)
                .build();
    }
}
