package shoppingmall.core.web.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.board.Board;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardFindResponseDto {

    private Long boardId;
    private String title;
    private String author;
    private String content;
    private int views;
    private LocalDateTime createdDate;

    @Builder
    public BoardFindResponseDto(Long boardId, String title, String author, String content, int views, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.views = views;
        this.createdDate = createdDate;
    }

    public static BoardFindResponseDto toResponseDto(Board entity) {
        return BoardFindResponseDto.builder()
                .boardId(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .content(entity.getContent())
                .views(entity.getViews())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
