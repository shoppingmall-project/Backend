package shoppingmall.core.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.comment.Comment;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardCreateResponseDto;
import shoppingmall.core.web.dto.board.BoardFindResponseDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;
import shoppingmall.core.web.dto.comment.CommentFindResponseDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    @Override
    public ResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));
        board.updateBoard(requestDto.getTitle(), requestDto.getContent());
        BoardCreateResponseDto responseDto = new BoardCreateResponseDto(board.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto deleteBoard(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));
        boardRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto findBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));

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
}
