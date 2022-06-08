package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.service.QandA.answer.AnswerService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.AnswerCreateRequestDto;
import shoppingmall.core.web.dto.answer.AnswerUpdateRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/question/{questionId}/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping()
    public ResponseDto createAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerCreateRequestDto requestDto, @AuthenticationPrincipal Member member) throws Exception {
        return answerService.createAnswer(questionId, requestDto, member.getId());
    }

    @PutMapping("/{answerId}")
    public ResponseDto updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @Valid @RequestBody AnswerUpdateRequestDto requestDto, @AuthenticationPrincipal Member member) {
        return answerService.updateAnswer(questionId, answerId, requestDto, member.getId());
    }

    @DeleteMapping("/{answerId}")
    public ResponseDto deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @AuthenticationPrincipal Member member) {
        return answerService.deleteAnswer(questionId, answerId, member.getId());
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
