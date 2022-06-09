package shoppingmall.core.service.board;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

public interface BoardService {

    ResponseDto createBoard(BoardCreateRequestDto requestDto, Long memberId);

    ResponseDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto, Long memberId);

    ResponseDto deleteBoard(Long boardId, Long memberId);

    ResponseDto findBoardById(Long boardId) throws Exception;

    ResponseDto findBoardList();

    ResponseDto findBoardByTitle(String title);

    ResponseDto findBoardByWriter(String writer);
}
