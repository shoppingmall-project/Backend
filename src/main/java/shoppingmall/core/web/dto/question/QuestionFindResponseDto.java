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
    private Long memberId;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private int answerNum;

    @Builder
    public QuestionFindResponseDto(Long id, Long memberId, String writer, String title, String content, LocalDateTime createdDate, int answerNum) {
        this.id = id;
        this.memberId = memberId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.answerNum = answerNum;
    }

    public static QuestionFindResponseDto toResponseDto(Question entity) {
        return QuestionFindResponseDto.builder()
                .id(entity.getId())
                .memberId(entity.getMember().getId())
                .writer(entity.getMember().getName())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdDate(entity.getCreatedDate())
                .answerNum(entity.getAnswerNum())
                .build();
    }

}
