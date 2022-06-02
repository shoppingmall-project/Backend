package shoppingmall.core.service.QandA.question;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.question.QuestionCreateRequestDto;
import shoppingmall.core.web.dto.question.QuestionUpdateRequestDto;


public interface QuestionService {

    ResponseDto createQuestion(QuestionCreateRequestDto requestDto);

    ResponseDto updateQuestion(Long questionId, QuestionUpdateRequestDto requestDto);

    ResponseDto deleteQuestion(Long questionId);

    ResponseDto findQuestionById(Long questionId);

    ResponseDto findQuestionList();
}
