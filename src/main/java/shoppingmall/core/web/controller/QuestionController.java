package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.QandA.question.QuestionService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.question.QuestionCreateRequestDto;
import shoppingmall.core.web.dto.question.QuestionUpdateRequestDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping()
    public ResponseDto createQuestion(@RequestBody QuestionCreateRequestDto requestDto, HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        Long memberId = (Long) session.getAttribute("memberId");
        return questionService.createQuestion(requestDto, memberId);
    }

    @PutMapping("/{questionId}")
    public ResponseDto updateQuestion(@PathVariable Long questionId, @RequestBody QuestionUpdateRequestDto requestDto, HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        Long memberId = (Long) session.getAttribute("memberId");
        return questionService.updateQuestion(questionId, requestDto, memberId);
    }

    @DeleteMapping("/{questionId}")
    public ResponseDto deleteQuestion(@PathVariable Long questionId) {
        return questionService.deleteQuestion(questionId);
    }

    @GetMapping("/{questionId}")
    public ResponseDto findQuestionById(@PathVariable Long questionId) {
        return questionService.findQuestionById(questionId);
    }

    @GetMapping()
    public ResponseDto findQuestionList() {
        return questionService.findQuestionList();
    }
}
