package shoppingmall.core.web.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Board;

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
    private int views;

    @Builder
    public BoardCreateRequestDto(String title, String author, String content, int views) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.views = views;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .author(author)
                .content(content)
                .views(views)
                .build();
    }
}
