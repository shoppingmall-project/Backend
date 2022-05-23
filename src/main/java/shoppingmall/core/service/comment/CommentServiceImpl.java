package shoppingmall.core.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.comment.Comment;
import shoppingmall.core.domain.comment.CommentRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.comment.CommentCreateRequestDto;
import shoppingmall.core.web.dto.comment.CommentCreateResponseDto;
import shoppingmall.core.web.dto.comment.CommentFindResponseDto;
import shoppingmall.core.web.dto.comment.CommentUpdateRequestDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseDto createComment(Long boardId, CommentCreateRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        Comment comment = requestDto.toEntity();

        comment.setBoard(board);
        commentRepository.save(comment);

        CommentCreateResponseDto responseDto = new CommentCreateResponseDto(comment.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findCommentList(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<Comment> commentList = commentRepository.findAllByBoardId(boardId);

        List<CommentFindResponseDto> responseDtoList = new ArrayList<>();
        for(Comment comment : commentList) {
            CommentFindResponseDto responseDto = CommentFindResponseDto.toResponseDto(comment);
            responseDtoList.add(responseDto);
        }

        return new ResponseDto("SUCCESS", responseDtoList);

    }

    @Override
    public ResponseDto findCommentById(Long boardId, Long id) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        CommentFindResponseDto responseDto = CommentFindResponseDto.toResponseDto(comment);

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto deleteComment(Long boardId, Long id) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        commentRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
    }

    @Transactional
    @Override
    public ResponseDto updateComment(Long boardId, Long id, CommentUpdateRequestDto requestDto) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        comment.updateComment(requestDto.getContent());

        CommentCreateResponseDto responseDto = new CommentCreateResponseDto(comment.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }
}
