package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.config.CustomUserDetailService;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.member.MemberService;
import shoppingmall.core.web.dto.*;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;
    
    @GetMapping(value = "/hello")
    public ResponseDto hello(@AuthenticationPrincipal Member member) {
        System.out.println("name = " + member);
        return new ResponseDto("HELLO", member.getId());
    }

    // 회원가입
    @PostMapping()
    public ResponseDto join(@Valid @RequestBody MemberCreateRequestDto user) throws Exception {
        return memberService.createMember(user);
    }
    // 회원 탈퇴
    @DeleteMapping("/{memberId}")
    public ResponseDto deleteMember(@PathVariable Long memberId) {
        return memberService.deleteMember(memberId);
    }

    // 회원 수정
    @PutMapping("/{memberId}")
    public ResponseDto updateMember(@PathVariable Long memberId, @Valid @RequestBody MemberUpdateRequestDto requestDto){
        return memberService.updateMember(memberId, requestDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseDto login(@Valid @RequestBody LoginRequestDto user, HttpServletRequest request) {
        return memberService.login(user, request);
    }
    // 회원 리스트 조회
    @GetMapping()
    public ResponseDto findMemberList(@AuthenticationPrincipal Member member) {
        return memberService.findMemberList(member.getId());
    }

    // 회원 조회
    @GetMapping("/{memberId}")
    public ResponseDto findMemberById(@PathVariable Long memberId) {
        return memberService.findMemberById(memberId);
    }
}

