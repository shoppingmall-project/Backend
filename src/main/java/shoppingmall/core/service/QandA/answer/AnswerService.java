package shoppingmall.core.service.QandA.answer;


import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.AnswerCreateRequestDto;
import shoppingmall.core.web.dto.answer.AnswerUpdateRequestDto;

public interface AnswerService {

    ResponseDto createAnswer(Long questionId, AnswerCreateRequestDto requestDto) throws Exception;

    ResponseDto deleteAnswer(Long questionId, Long answerId);

    ResponseDto updateAnswer(Long questionId, Long answerId, AnswerUpdateRequestDto requestDto);

    ResponseDto findAnswerById(Long questionId, Long answerId);

    ResponseDto findAnswerList(Long questionId);
}
