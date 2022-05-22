package shoppingmall.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.Board;
import shoppingmall.core.domain.BoardRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardCreateResponseDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

import javax.transaction.Transactional;
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
        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto deleteBoard(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));
        boardRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto findBoardById(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));
        Board board = boardRepository.findBoardById(id);

        return new ResponseDto("SUCCESS", board);
    }

    @Override
    public ResponseDto findBoardList() {
        List<Board> boardList = boardRepository.findBoardList();
        return new ResponseDto("SUCCESS", boardList);
    }
}
