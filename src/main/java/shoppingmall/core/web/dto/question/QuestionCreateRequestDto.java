package shoppingmall.core.web.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.QandA.question.Question;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class QuestionCreateRequestDto {

    private String writer;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    @Builder
    public QuestionCreateRequestDto(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Question toEntity() {
        return Question.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .answerNum(0)
                .build();
    }
}
