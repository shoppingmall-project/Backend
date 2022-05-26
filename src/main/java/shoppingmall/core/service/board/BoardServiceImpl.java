package shoppingmall.core.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{


    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public ResponseDto createBoard(BoardCreateRequestDto requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        BoardCreateResponseDto responseDto = new BoardCreateResponseDto(board.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto) {
        Board board = checkValidBoard(id);
        board.updateBoard(requestDto.getTitle(), requestDto.getContent());
        BoardUpdateResponseDto responseDto = new BoardUpdateResponseDto(board.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteBoard(Long id) {
        checkValidBoard(id);
        boardRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto findBoardById(Long id) throws IllegalArgumentException {
        Board board = checkValidBoard(id);

        BoardFindResponseDto responseDto = BoardFindResponseDto.toResponseDto(board);
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findBoardList() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardFindResponseDto> responseDtoList = new ArrayList<>();
        for(Board board : boardList) {
            BoardFindResponseDto responseDto = BoardFindResponseDto.toResponseDto(board);
            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }

    private Board checkValidBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));
    }
}
