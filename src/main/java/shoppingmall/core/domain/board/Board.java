package shoppingmall.core.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.BaseTimeEntity;
import shoppingmall.core.domain.member.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(length = 30, nullable = false)
    private int views;

    @Builder
    public Board(Long id, Member member, String title, String content, int views) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.views = views;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
