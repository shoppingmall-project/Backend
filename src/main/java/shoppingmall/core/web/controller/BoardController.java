package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.service.board.BoardService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseDto findBoardList() {
        return boardService.findBoardList();
    }

    @PostMapping()
    public ResponseDto createBoard(@Valid @RequestBody BoardCreateRequestDto requestDto, @AuthenticationPrincipal Member member) {
        System.out.println("member = " + member);
        return boardService.createBoard(requestDto, member.getId());
    }

    @PutMapping("/{boardId}")
    public ResponseDto updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequestDto requestDto, @AuthenticationPrincipal Member member) {
        return boardService.updateBoard(boardId, requestDto, member.getId());
    }

    @DeleteMapping("/{boardId}")
    public ResponseDto deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal Member member) {
        return boardService.deleteBoard(boardId, member.getId());
    }

    @GetMapping("/{boardId}")
    public ResponseDto findBoardById(@PathVariable Long boardId) throws Exception {
        return boardService.findBoardById(boardId);
    }

    @GetMapping("/title/{title}")
    public ResponseDto findBoardByTitle(@PathVariable String title) {
        return boardService.findBoardByTitle(title);
    }

    @GetMapping("/writer/{writer}")
    public ResponseDto findBoardByWriter(@PathVariable String writer) {
        return boardService.findBoardByWriter(writer);
    }
}
