package shoppingmall.core.service.QandA.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.QandA.answer.Answer;
import shoppingmall.core.domain.QandA.question.Question;
import shoppingmall.core.domain.QandA.question.QuestionRepository;
import shoppingmall.core.domain.QandA.answer.AnswerRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.answer.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public ResponseDto createAnswer(Long question_id, AnswerCreateRequestDto requestDto, Long memberId) throws Exception {
        Member member = checkValidMemberId(memberId);
        if (!Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "권한이 없습니다..");
        }
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
    public ResponseDto deleteAnswer(Long question_id, Long answer_id, Long memberId) {
        Member member = checkValidMemberId(memberId);

        if (!Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "권한이 없습니다..");
        }

        Question question = checkValidQuestionId(question_id);
        question.setAnswerNum(question.getAnswerNum() - 1);
        checkValidAnswerId(answer_id);

        answerRepository.deleteById(answer_id);
        return new ResponseDto("SUCCESS");
    }

    @Transactional
    @Override
    public ResponseDto updateAnswer(Long question_id, Long answer_id, AnswerUpdateRequestDto requestDto, Long memberId) {
        Member member = checkValidMemberId(memberId);

        if (!Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "권한이 없습니다..");
        }
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
    private Member checkValidMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException(" 존재하지 않는 유저입니다. "));
    }
}
