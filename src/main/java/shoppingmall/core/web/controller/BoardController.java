package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.board.BoardService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping()
    public ResponseDto createBoard(@Valid @RequestBody BoardCreateRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @PutMapping("/{boardId}")
    public ResponseDto updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.updateBoard(boardId, requestDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseDto deleteBoard(@PathVariable Long boardId) {
        return boardService.deleteBoard(boardId);
    }

    @GetMapping("/{boardId}")
    public ResponseDto findBoardById(@PathVariable Long boardId) throws Exception{
        return boardService.findBoardById(boardId);
    }

    @GetMapping()
    public ResponseDto findBoardList() {
        return boardService.findBoardList();
    }
}
