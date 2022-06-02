package shoppingmall.core.service.QandA.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.QandA.answer.Answer;
import shoppingmall.core.domain.QandA.question.Question;
import shoppingmall.core.domain.QandA.question.QuestionRepository;
import shoppingmall.core.domain.QandA.answer.AnswerRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public ResponseDto createAnswer(Long question_id, AnswerCreateRequestDto requestDto) throws Exception {
        Question question = checkValidQuestionId(question_id);
        question.setAnswerNum(question.getAnswerNum() + 1);
        Answer answer = requestDto.toEntity();
        answer.setQuestion(question);
        Answer savedAnswer = answerRepository.save(answer);

        AnswerCreateResponseDto responseDto = new AnswerCreateResponseDto(savedAnswer.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteAnswer(Long question_id, Long answer_id) {
        Question question = checkValidQuestionId(question_id);
        question.setAnswerNum(question.getAnswerNum() - 1);
        checkValidAnswerId(answer_id);

        answerRepository.deleteById(answer_id);
        return new ResponseDto("SUCCESS");
    }

    @Transactional
    @Override
    public ResponseDto updateAnswer(Long question_id, Long answer_id, AnswerUpdateRequestDto requestDto) {
        checkValidQuestionId(question_id);
        Answer answer = checkValidAnswerId(answer_id);
        answer.updateAnswer(requestDto.getContent());

        AnswerUpdateResponseDto responseDto = new AnswerUpdateResponseDto(answer.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findAnswerById(Long question_id, Long answer_id) {
        checkValidQuestionId(question_id);
        Answer answer = checkValidAnswerId(answer_id);
        AnswerFindResponseDto responseDto = AnswerFindResponseDto.toResponseDto(answer);

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findAnswerList(Long question_id) {
        checkValidQuestionId(question_id);
        List<Answer> answerList = answerRepository.findAllByQuestionId(question_id);

        List<AnswerFindResponseDto> responseDtoList = new ArrayList<>();
        for (Answer answer : answerList) {
            AnswerFindResponseDto responseDto = AnswerFindResponseDto.toResponseDto(answer);
            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }


    private Answer checkValidAnswerId(Long answer_id) {
        return answerRepository.findById(answer_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
    }

    private Question checkValidQuestionId(Long question_id) {
        return questionRepository.findById(question_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
    }
}
