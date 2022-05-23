package shoppingmall.core.web.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.service.comment.CommentService;
import shoppingmall.core.service.storage.StorageService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.comment.CommentCreateRequestDto;
import shoppingmall.core.web.dto.comment.CommentUpdateRequestDto;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/board/{boardId}/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    //댓글 생성
    @PostMapping()
    public ResponseDto createComment(@PathVariable Long boardId, @ModelAttribute CommentCreateRequestDto requestDto,
                                     @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
        return commentService.createComment(boardId, requestDto, file);
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
    public ResponseDto deleteComment(@PathVariable Long boardId, @PathVariable Long id) throws Exception {
        return commentService.deleteComment(boardId, id);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseDto updateComment(@PathVariable Long boardId, @PathVariable Long id,
                                     @Valid @ModelAttribute CommentUpdateRequestDto requestDto,
                                     @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return commentService.updateComment(boardId, id, requestDto, file);
    }
}
