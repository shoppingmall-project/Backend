package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.QandA.answer.AnswerService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.AnswerCreateRequestDto;
import shoppingmall.core.web.dto.answer.AnswerUpdateRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question/{questionId}/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping()
    public ResponseDto createAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerCreateRequestDto requestDto, HttpSession session) throws Exception {
        Long memberId = createSession(session);
        return answerService.createAnswer(questionId, requestDto, memberId);
    }

    @PutMapping("/{answerId}")
    public ResponseDto updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @Valid @RequestBody AnswerUpdateRequestDto requestDto, HttpSession session) {
        Long memberId = createSession(session);
        return answerService.updateAnswer(questionId, answerId, requestDto, memberId);
    }

    @DeleteMapping("/{answerId}")
    public ResponseDto deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        Long memberId = createSession(session);
        return answerService.deleteAnswer(questionId, answerId, memberId);
    }

    @GetMapping("/{answerId}")
    public ResponseDto findAnswerById(@PathVariable Long questionId, @PathVariable Long answerId) {
        return answerService.findAnswerById(questionId, answerId);
    }

    @GetMapping()
    public ResponseDto findAnswerList(@PathVariable Long questionId) {
        return answerService.findAnswerList(questionId);
    }

    private Long createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        return (Long) session.getAttribute("memberId");
    }
}
