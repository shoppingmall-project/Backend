package shoppingmall.core.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.TokenDto;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;

import javax.validation.Valid;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    MemberRepository memberRepository;

    @Override
    public ResponseDto login(LoginRequestDto user) {
        Member member = memberRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        TokenDto tokenDto = new TokenDto(member, jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        return new ResponseDto("SUCCESS", tokenDto);
    }

    @Override
    public ResponseDto deleteMember(Long id) {
        memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        memberRepository.deleteById(id);;

        return new ResponseDto("SUCCESS");
    }

    @Override
    public Long createMember(MemberCreateRequestDto requestDto) throws Exception {
        if (memberRepository.findByAccount(requestDto.getAccount()).isPresent()) {
            throw new IllegalArgumentException(requestDto.getAccount() + "는 이미 존재하는 아이디입니다.");
        }

        return memberRepository.save(Member.builder()
                .account(requestDto.getAccount())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .email(requestDto.getEmail())
                .gender(requestDto.getGender())
                .role(requestDto.getRole())
                .roles(Collections.singletonList("Role_" + requestDto.getRole()))
                .build()).getId();
    }

}

