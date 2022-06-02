package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.QandA.answer.AnswerService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.AnswerCreateRequestDto;
import shoppingmall.core.web.dto.answer.AnswerUpdateRequestDto;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question/{questionId}/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping()
    public ResponseDto createAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerCreateRequestDto requestDto) throws Exception {
        return answerService.createAnswer(questionId, requestDto);
    }

    @PutMapping("/{answerId}")
    public ResponseDto updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @Valid @RequestBody AnswerUpdateRequestDto requestDto) {
        return answerService.updateAnswer(questionId, answerId, requestDto);
    }

    @DeleteMapping("/{answerId}")
    public ResponseDto deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        return answerService.deleteAnswer(questionId, answerId);
    }

    @GetMapping("/{answerId}")
    public ResponseDto findAnswerById(@PathVariable Long questionId, @PathVariable Long answerId) {
        return answerService.findAnswerById(questionId, answerId);
    }

    @GetMapping()
    public ResponseDto findAnswerList(@PathVariable Long questionId) {
        return answerService.findAnswerList(questionId);
    }
}
