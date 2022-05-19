package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.login.MemberService;
import shoppingmall.core.web.dto.*;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberResponseDto;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final UserDetailsService userDetailsService;

    // 회원가입
    @PostMapping("/signup")
    public Long join(@Valid @RequestBody MemberCreateRequestDto user) throws Exception {
        log.info("signup");
        return memberService.createMember(user);
    }
    // 회원 탈퇴
    @DeleteMapping("/auth/{id}/delete")
    public ResponseDto deleteMember(@PathVariable Long id) {
        log.info("delete");
        return memberService.deleteMember(id);
    }
    // 로그인
    @PostMapping("/auth/login")
    public ResponseDto login(@Valid @RequestBody LoginRequestDto user) {
        log.info("login");
        return memberService.login(user);
    }

    // 회원 리스트 조회
    @GetMapping("/member")
    public List<Member> findMember() {
        log.info("member list");
        return memberRepository.findAllMember();
    }


    // 사실 뭔지 잘 모르겠음. --------------------
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
    //------------------------------------
}

