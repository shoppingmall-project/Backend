package shoppingmall.core.domain.QandA.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.BaseTimeEntity;
import shoppingmall.core.domain.QandA.question.Question;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(length = 200, nullable = false)
    private String content;


    @Column(length = 256, nullable = false)
    private String password;

    @Builder
    public Answer(Long id, Question question, String content, String password) {
        this.id = id;
        this.question = question;
        this.content = content;
        this.password = password;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void updateAnswer(String content) {
        this.content = content;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
