package shoppingmall.core.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.comment.Comment;
import shoppingmall.core.domain.comment.CommentRepository;
import shoppingmall.core.service.storage.StorageService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.comment.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Qualifier("FileStorageService")
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final StorageService storageService;


    @Transactional
    @Override
    public ResponseDto createComment(Long boardId, CommentCreateRequestDto requestDto, MultipartFile file) throws Exception{

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        Comment comment = requestDto.toEntity();
        comment.setBoard(board);

        Comment savedComment = commentRepository.save(comment);

        if(file != null) {
            String path = "/board_" + boardId + "/comment_" + savedComment.getId() + "/image";
            String uploadedFilePath = storageService.store(path, file);

            savedComment.updateUrl(uploadedFilePath);
        }
        CommentCreateResponseDto responseDto = CommentCreateResponseDto.builder()
                .id(savedComment.getId())
                .build();

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto updateComment(Long boardId, Long id, CommentUpdateRequestDto requestDto,  MultipartFile file) throws Exception {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        comment.updateComment(requestDto.getContent());

        if(comment.getImageUrl() != null) {
            if (storageService.delete(comment.getImageUrl())) {
                comment.updateUrl(null);
            }
        }
        if(file != null) {
            String path = "/board_" + boardId + "/comment_" + comment.getId() + "/image";
            String uploadedFilePath = storageService.store(path, file);

            comment.updateUrl(uploadedFilePath);
        }

        CommentUpdateResponseDto responseDto = new CommentUpdateResponseDto(comment.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteComment(Long boardId, Long id) throws Exception{
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        if(comment.getImageUrl() != null) {
            if (storageService.delete(comment.getImageUrl())) {
                comment.updateUrl(null);
            }
        }
        commentRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
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
}
