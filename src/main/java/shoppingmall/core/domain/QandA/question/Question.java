package shoppingmall.core.domain.QandA.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String writer;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    @Column
    private Boolean isAnswered;

    @Column
    private int answerNum;

    @Builder
    public Question(Long id, String writer, String title, String content, Boolean isAnswered, int answerNum) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.isAnswered = isAnswered;
        this.answerNum = answerNum;
    }

    public void updateQuestion(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }
}
