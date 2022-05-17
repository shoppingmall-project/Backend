package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.Member;
import shoppingmall.core.domain.MemberRepository;
import shoppingmall.core.web.dto.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;

    // 회원가입
    @PostMapping("/signup")
    public Long join(@Valid @RequestBody MemberCreateRequestDto user) {
        if (memberRepository.findByAccount(user.getAccount()).isPresent()) {
            throw new IllegalArgumentException(user.getAccount() + "이미 존재하는 아이디입니다.");
        }

        return memberRepository.save(Member.builder()
                .account(user.getAccount())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .roles(Collections.singletonList("Role_"+ user.getRole()))
                .build()).getId();

    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseDto login(@Valid @RequestBody LoginRequestDto user) {
        Member member = memberRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        TokenDto tokenDto = new TokenDto(member, jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        return new ResponseDto("SUCCESS", tokenDto);
    }

    @GetMapping("/member")
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    @GetMapping("/student")
    public String student() {
        return "hello student!";
    }

    @GetMapping("/user")
    public ResponseDto userInfo(ServletRequest request) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        Member member = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return new ResponseDto("SUCCESS", memberResponseDto);
    }

    @GetMapping("/auth/check")
    public ResponseDto authcheck(ServletRequest request) {
        return new ResponseDto("SUCCESS");
    }
}

