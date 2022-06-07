package shoppingmall.core.service.QandA.answer;


import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.AnswerCreateRequestDto;
import shoppingmall.core.web.dto.answer.AnswerUpdateRequestDto;

public interface AnswerService {

    ResponseDto createAnswer(Long questionId, AnswerCreateRequestDto requestDto, Long memberId) throws Exception;

    ResponseDto deleteAnswer(Long questionId, Long answerId, Long memberId);

    ResponseDto updateAnswer(Long questionId, Long answerId, AnswerUpdateRequestDto requestDto, Long memberId);

    ResponseDto findAnswerById(Long questionId, Long answerId);

    ResponseDto findAnswerList(Long questionId);
}
