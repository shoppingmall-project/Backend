package shoppingmall.core.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public ResponseDto createBoard(BoardCreateRequestDto requestDto, Long memberId) {
        Member member = checkValidMember(memberId);
        Board board = requestDto.toEntity();
        board.setMember(member);

        boardRepository.save(board);

        BoardCreateResponseDto responseDto = new BoardCreateResponseDto(board.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto updateBoard(Long boardId, BoardUpdateRequestDto requestDto, Long memberId) {
        Board board = checkValidBoard(boardId);
        Member member = checkValidMember(memberId);
        if (!Objects.equals(board.getMember().getId(), memberId) || !Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "작성자와 다른 아이디입니다.");
        }
        board.updateBoard(requestDto.getTitle(), requestDto.getContent());
        BoardUpdateResponseDto responseDto = new BoardUpdateResponseDto(board.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteBoard(Long boardId) {

        checkValidBoard(boardId);
        boardRepository.deleteById(boardId);

        return new ResponseDto("SUCCESS");
    }

    @Transactional
    @Override
    public ResponseDto findBoardById(Long boardId) throws IllegalArgumentException {
        Board board = checkValidBoard(boardId);
        board.setViews(board.getViews() + 1);

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

    private Board checkValidBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다"));
    }

    private Member checkValidMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));
    }
}
