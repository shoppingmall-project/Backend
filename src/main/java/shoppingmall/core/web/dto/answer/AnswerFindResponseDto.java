package shoppingmall.core.web.dto.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.QandA.answer.Answer;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AnswerFindResponseDto {

    private Long id;
    private Long question_id;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public AnswerFindResponseDto(Long id, Long question_id, String content, LocalDateTime createdDate) {
        this.id = id;
        this.question_id = question_id;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static AnswerFindResponseDto toResponseDto(Answer entity) {
        return AnswerFindResponseDto.builder()
                .id(entity.getId())
                .question_id(entity.getQuestion().getId())
                .content(entity.getContent())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
