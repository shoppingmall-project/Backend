package shoppingmall.core.service.comment;

import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.comment.CommentCreateRequestDto;
import shoppingmall.core.web.dto.comment.CommentUpdateRequestDto;

public interface CommentService {

    ResponseDto createComment(Long boardId, CommentCreateRequestDto requestDto, MultipartFile file) throws Exception;

    ResponseDto findCommentList(Long boardId);

    ResponseDto findCommentById(Long boardId, Long id);

    ResponseDto deleteComment(Long boardId, Long id) throws Exception;

    ResponseDto updateComment(Long boardId, Long id, CommentUpdateRequestDto requestDto, MultipartFile file) throws Exception;
}
