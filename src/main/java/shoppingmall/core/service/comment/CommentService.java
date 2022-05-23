package shoppingmall.core.service.comment;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.comment.CommentCreateRequestDto;
import shoppingmall.core.web.dto.comment.CommentUpdateRequestDto;

public interface CommentService {

    ResponseDto createComment(Long boardId, CommentCreateRequestDto requestDto);

    ResponseDto findCommentList(Long boardId);

    ResponseDto findCommentById(Long boardId, Long id);

    ResponseDto deleteComment(Long boardId, Long id);

    ResponseDto updateComment(Long boardId, Long id, CommentUpdateRequestDto requestDto);
}
