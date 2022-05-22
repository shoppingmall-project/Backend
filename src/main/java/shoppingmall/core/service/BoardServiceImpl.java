package shoppingmall.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.Board;
import shoppingmall.core.domain.BoardRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardCreateResponseDto;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Override
    public ResponseDto createBoard(BoardCreateRequestDto requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        BoardCreateResponseDto responseDto = new BoardCreateResponseDto(board.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }
}
