package shoppingmall.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column(length = 30, nullable = false)
    private int views;

    @Builder
    public Board(Long id, String title, String author, String content, int views) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.views = views;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
