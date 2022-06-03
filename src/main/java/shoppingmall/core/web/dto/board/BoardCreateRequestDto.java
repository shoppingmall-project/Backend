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
    private String content;

    @Builder
    public BoardCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .views(0)
                .build();
    }
}
