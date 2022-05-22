package shoppingmall.core.service;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

public interface BoardService {

    ResponseDto createBoard(BoardCreateRequestDto requestDto);

    ResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto);

    ResponseDto deleteBoard(Long id);

    ResponseDto findBoardById(Long id);

    ResponseDto findBoardList();
}
