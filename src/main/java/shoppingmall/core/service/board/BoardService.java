package shoppingmall.core.service.board;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

public interface BoardService {

    ResponseDto createBoard(BoardCreateRequestDto requestDto);

    ResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto);

    ResponseDto deleteBoard(Long id);

    ResponseDto findBoardById(Long id) throws Exception;

    ResponseDto findBoardList();
}
