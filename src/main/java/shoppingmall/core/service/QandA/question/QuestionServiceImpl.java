package shoppingmall.core.service.QandA.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.QandA.question.Question;
import shoppingmall.core.domain.QandA.question.QuestionRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.question.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    @Transactional
    @Override
    public ResponseDto createQuestion(QuestionCreateRequestDto requestDto) {
        Question question = questionRepository.save(requestDto.toEntity());
        question.setAnswered(Boolean.FALSE);

        QuestionCreateResponseDto responseDto = new QuestionCreateResponseDto(question.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }
    @Transactional
    @Override
    public ResponseDto updateQuestion(Long questionId, QuestionUpdateRequestDto requestDto) {
        Question question = checkValidQuestionId(questionId);
        question.updateQuestion(requestDto.getTitle(), requestDto.getContent());

        QuestionUpdateResponseDto responseDto = new QuestionUpdateResponseDto(question.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }
    @Transactional
    @Override
    public ResponseDto deleteQuestion(Long questionId) {
        checkValidQuestionId(questionId);

        questionRepository.deleteById(questionId);
        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto findQuestionById(Long questionId) {
        Question question = checkValidQuestionId(questionId);
        QuestionFindResponseDto responseDto = QuestionFindResponseDto.toResponseDto(question);

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findQuestionList() {
        List<Question> questionList = questionRepository.findAll();

        List<QuestionFindResponseDto> responseDtoList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionFindResponseDto responseDto = QuestionFindResponseDto.toResponseDto(question);
            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }
    private Question checkValidQuestionId(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("해당 질문이 없습니다."));
    }
}
