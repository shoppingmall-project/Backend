package shoppingmall.core.web.dto.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.QandA.question.Question;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QuestionFindResponseDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private Boolean isAnswered;
    private LocalDateTime createdDate;
    private int answerNum;

    @Builder
    public QuestionFindResponseDto(Long id, String writer, String title, String content, Boolean isAnswered, LocalDateTime createdDate, int answerNum) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.isAnswered = isAnswered;
        this.createdDate = createdDate;
        this.answerNum = answerNum;
    }

    public static QuestionFindResponseDto toResponseDto(Question entity) {
        return QuestionFindResponseDto.builder()
                .id(entity.getId())
                .writer(entity.getMember().getName())
                .title(entity.getTitle())
                .content(entity.getContent())
                .isAnswered(entity.getIsAnswered())
                .createdDate(entity.getCreatedDate())
                .answerNum(entity.getAnswerNum())
                .build();
    }

}
