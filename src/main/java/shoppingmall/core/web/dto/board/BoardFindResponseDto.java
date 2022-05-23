package shoppingmall.core.web.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.board.Board;

@Getter
@NoArgsConstructor
public class BoardFindResponseDto {

    private Long boardId;
    private String title;
    private String author;
    private String content;
    private int views;

    @Builder
    public BoardFindResponseDto(Long boardId, String title, String author, String content, int views) {
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.views = views;
    }

    public static BoardFindResponseDto toResponseDto(Board entity) {
        return BoardFindResponseDto.builder()
                .boardId(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .content(entity.getContent())
                .views(entity.getViews())
                .build();
    }
}
