package shoppingmall.core.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.TokenDto;
import shoppingmall.core.web.dto.member.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public ResponseDto login(LoginRequestDto user, HttpServletRequest request) {
        Member member = memberRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        TokenDto tokenDto = new TokenDto(member, jwtTokenProvider.createToken(member.getUsername(), member.getRoles(), member.getId()));
        return new ResponseDto("SUCCESS", tokenDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteMember(Long id) {
        checkValidMember(id);
        memberRepository.deleteById(id);;

        return new ResponseDto("SUCCESS");
    }

    @Override
    @Transactional
    public ResponseDto updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = checkValidMember(id);
        member.updateMember(passwordEncoder.encode(requestDto.getPassword()), requestDto.getRole(),
                requestDto.getEmail(), requestDto.getAddress(), requestDto.getPhoneNum());

        MemberUpdateResponseDto responseDto = MemberUpdateResponseDto.builder().account(member.getAccount()).build();
        return new ResponseDto("SUCCESS",responseDto);
    }

    @Transactional
    @Override
    public ResponseDto createMember(MemberCreateRequestDto requestDto) throws Exception {
        if (memberRepository.existsByAccount(requestDto.getAccount())) {
            throw new IllegalArgumentException(requestDto.getAccount() + "는 이미 존재하는 아이디입니다.");
        }
        Member member = memberRepository.save(Member.builder()
                .account(requestDto.getAccount())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .email(requestDto.getEmail())
                .gender(requestDto.getGender())
                .role(requestDto.getRole())
                .roles(Collections.singletonList("Role_" + requestDto.getRole()))
                .address(requestDto.getAddress())
                .phoneNum(requestDto.getPhoneNum())
                .build());

        MemberCreateResponseDto responseDto = new MemberCreateResponseDto(member.getId(), member.getAccount());

        return new ResponseDto("SUCCESS", responseDto);

    }

    @Override
    public ResponseDto findMemberList(Long memberId) {
        Member login_member = checkValidMember(memberId);

        if (!Objects.equals(login_member.getRole(), "M")) {
            return new ResponseDto("FAIL", "권한이 없습니다..");
        }

        List<Member> memberList = memberRepository.findAll();

        List<MemberFindResponseDto> responseDtoList = new ArrayList<>();
        for (Member member : memberList) {
            MemberFindResponseDto responseDto = MemberFindResponseDto.toResponseDto(member);
            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }

    @Override
    public ResponseDto findMemberById(Long id) {
        Member member = checkValidMember(id);

        MemberFindResponseDto responseDto = MemberFindResponseDto.toResponseDto(member);
        return new ResponseDto("SUCCESS", responseDto);
    }

    private Member checkValidMember(Long id) {
        return memberRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

}

