package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.member.MemberService;
import shoppingmall.core.web.dto.*;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberResponseDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final UserDetailsService userDetailsService;

    // 회원가입
    @PostMapping("/signup")
    public String join(@Valid @RequestBody MemberCreateRequestDto user) throws Exception {
        log.info("signup");
        return memberService.createMember(user);
    }
    // 회원 탈퇴
    @DeleteMapping("/auth/{id}")
    public ResponseDto deleteMember(@PathVariable Long id) {
        log.info("delete");
        return memberService.deleteMember(id);
    }

    // 회원 수정
    @PutMapping("/auth/{id}")
    public ResponseDto updateMember(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequestDto requestDto){
        return memberService.updateMember(id, requestDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseDto login(@Valid @RequestBody LoginRequestDto user) {
        log.info("login");
        return memberService.login(user);
    }

    // 회원 리스트 조회
    @GetMapping("/memberlist")
    public ResponseDto findMemberList() {
        log.info("member list");
        return memberService.findMemberList();
    }

    // 회원 조회
    @GetMapping("/member/{id}")
    public ResponseDto findMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
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

