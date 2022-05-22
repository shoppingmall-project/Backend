package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.BoardRepository;
import shoppingmall.core.service.BoardService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/create")
    public ResponseDto createBoard(@Valid @RequestBody BoardCreateRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @PutMapping("/board/{id}")
    public ResponseDto updateBoard(@PathVariable Long id, @Valid @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/board/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }

    @GetMapping("/board/{id}")
    public ResponseDto findBoardById(@PathVariable Long id) {
        return boardService.findBoardById(id);
    }

    @GetMapping("/boardlist")
    public ResponseDto findBoardList() {
        return boardService.findBoardList();
    }
}
