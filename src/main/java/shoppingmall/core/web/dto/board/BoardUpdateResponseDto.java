package shoppingmall.core.web.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateResponseDto {

    private Long id;

    public BoardUpdateResponseDto(Long id) {
        this.id = id;
    }
}
