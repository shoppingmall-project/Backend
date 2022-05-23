package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.comment.CommentService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.comment.CommentCreateRequestDto;
import shoppingmall.core.web.dto.comment.CommentUpdateRequestDto;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/board/{boardId}/comment")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping()
    public ResponseDto createComment(@PathVariable Long boardId, @Valid @RequestBody CommentCreateRequestDto requestDto) {
        return commentService.createComment(boardId, requestDto);
    }

    //댓글 리스트 조회
    @GetMapping()
    public ResponseDto findCommentList(@PathVariable Long boardId) {
        return commentService.findCommentList(boardId);
    }

    //댓글 조회
    @GetMapping("/{id}")
    public ResponseDto findCommentById(@PathVariable Long boardId, @PathVariable Long id) {
        return commentService.findCommentById(boardId, id);
    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseDto deleteComment(@PathVariable Long boardId, @PathVariable Long id) {
        return commentService.deleteComment(boardId, id);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseDto updateComment(@PathVariable Long boardId, @PathVariable Long id, @Valid @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.updateComment(boardId, id, requestDto);
    }
}
