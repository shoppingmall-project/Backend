package shoppingmall.core.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.board.Board;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 30, nullable = false)
    private String author;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(length = 200)
    private String imageUrl;

    @Builder
    public Comment(Long id, Board board, String author, String content, String imageUrl) {
        this.id = id;
        this.board = board;
        this.author = author;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void updateUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
