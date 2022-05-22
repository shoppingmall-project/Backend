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
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping()
    public ResponseDto createBoard(@Valid @RequestBody BoardCreateRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseDto updateBoard(@PathVariable Long id, @Valid @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }

    @GetMapping("/{id}")
    public ResponseDto findBoardById(@PathVariable Long id) {
        return boardService.findBoardById(id);
    }

    @GetMapping()
    public ResponseDto findBoardList() {
        return boardService.findBoardList();
    }
}
