package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.core.service.BoardService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;

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
}
