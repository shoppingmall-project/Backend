package shoppingmall.core.web.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateResponseDto {

    private Long id;

    public BoardCreateResponseDto(Long id) {
        this.id = id;
    }
}
