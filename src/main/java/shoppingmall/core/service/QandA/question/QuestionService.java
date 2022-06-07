package shoppingmall.core.service.QandA.question;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.question.QuestionCreateRequestDto;
import shoppingmall.core.web.dto.question.QuestionUpdateRequestDto;


public interface QuestionService {

    ResponseDto createQuestion(QuestionCreateRequestDto requestDto, Long memberId);

    ResponseDto updateQuestion(Long questionId, QuestionUpdateRequestDto requestDto, Long memberId);

    ResponseDto deleteQuestion(Long questionId, Long memberId);

    ResponseDto findQuestionById(Long questionId);

    ResponseDto findQuestionList();
}
