package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping()
    public ResponseDto createBoard(@Valid @RequestBody BoardCreateRequestDto requestDto, HttpSession session) {
        Long memberId = createSession(session);
        return boardService.createBoard(requestDto, memberId);
    }

    @PostMapping(params = {"requestDto"})
    public ResponseDto createBoard(@Valid @RequestBody BoardCreateRequestDto requestDto) {
        return new ResponseDto("FAIL", "로그인을 하지 않았습니다.");
    }

    @PutMapping("/{boardId}")
    public ResponseDto updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequestDto requestDto, HttpSession session) {
        Long memberId = createSession(session);
        return boardService.updateBoard(boardId, requestDto, memberId);
    }

    @DeleteMapping("/{boardId}")
    public ResponseDto deleteBoard(@PathVariable Long boardId, HttpSession session) {
        Long memberId = createSession(session);
        return boardService.deleteBoard(boardId, memberId);
    }

    @GetMapping("/{boardId}")
    public ResponseDto findBoardById(@PathVariable Long boardId) throws Exception {
        return boardService.findBoardById(boardId);
    }

    @GetMapping()
    public ResponseDto findBoardList() {
        return boardService.findBoardList();
    }

    private Long createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        return (Long) session.getAttribute("memberId");
    }
}
