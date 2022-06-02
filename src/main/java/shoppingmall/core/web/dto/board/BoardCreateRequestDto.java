package shoppingmall.core.web.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.board.Board;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String content;

    @Builder
    public BoardCreateRequestDto(String title, String author, String content) {
        this.title = titit le;
        this.author = author;
        this.content = content;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .author(author)
                .content(content)
                .views(0)
                .build();
    }
}
