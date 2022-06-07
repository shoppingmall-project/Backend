package shoppingmall.core.service.QandA.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.QandA.question.Question;
import shoppingmall.core.domain.QandA.question.QuestionRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.question.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public ResponseDto createQuestion(QuestionCreateRequestDto requestDto, Long memberId) {
        Member member = checkValidMemberId(memberId);
        Question question = questionRepository.save(requestDto.toEntity());
        question.setAnswered(Boolean.FALSE);
        question.setMember(member);

        QuestionCreateResponseDto responseDto = new QuestionCreateResponseDto(question.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto updateQuestion(Long questionId, QuestionUpdateRequestDto requestDto, Long memberId) {
        memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException(" 존재하지 않는 유저입니다. "));

        Question question = checkValidQuestionId(questionId);
        if (!Objects.equals(question.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("권한이 없습니다..");
        }
        question.updateQuestion(requestDto.getTitle(), requestDto.getContent());

        QuestionUpdateResponseDto responseDto = new QuestionUpdateResponseDto(question.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteQuestion(Long questionId, Long memberId) {
        Member member = checkValidMemberId(memberId);
        Question question = checkValidQuestionId(questionId);

        if (!Objects.equals(question.getMember().getId(), memberId) && !Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "권한이 없습니다..");
        }
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

    private Member checkValidMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException(" 존재하지 않는 유저입니다. "));
    }
}
