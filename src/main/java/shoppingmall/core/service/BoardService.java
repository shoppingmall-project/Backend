package shoppingmall.core.service;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;

public interface BoardService {

    ResponseDto createBoard(BoardCreateRequestDto requestDto);
}
