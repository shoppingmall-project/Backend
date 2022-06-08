package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.service.QandA.answer.question.QuestionService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.question.QuestionCreateRequestDto;
import shoppingmall.core.web.dto.question.QuestionUpdateRequestDto;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping()
    public ResponseDto createQuestion(@RequestBody QuestionCreateRequestDto requestDto, @AuthenticationPrincipal Member member) {
        return questionService.createQuestion(requestDto, member.getId());
    }

    @PutMapping("/{questionId}")
    public ResponseDto updateQuestion(@PathVariable Long questionId, @RequestBody QuestionUpdateRequestDto requestDto, @AuthenticationPrincipal Member member) {
        return questionService.updateQuestion(questionId, requestDto, member.getId());
    }

    @DeleteMapping("/{questionId}")
    public ResponseDto deleteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal Member member) {
        return questionService.deleteQuestion(questionId, member.getId());
    }

    // 비밀글 여부 여기서 나타내면 될지도
    @GetMapping("/{questionId}")
    public ResponseDto findQuestionById(@PathVariable Long questionId) {
        return questionService.findQuestionById(questionId);
    }

    @GetMapping()
    public ResponseDto findQuestionList() {
        return questionService.findQuestionList();
    }
}
