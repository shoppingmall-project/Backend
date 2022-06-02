package shoppingmall.core.web.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AnswerUpdateRequestDto {

    @NotEmpty
    private String content;
    @NotEmpty
    private String password;

    @Builder
    public AnswerUpdateRequestDto(String content, String password) {
        this.content = content;
        this.password = password;
    }
}
