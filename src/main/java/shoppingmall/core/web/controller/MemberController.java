package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.member.MemberService;
import shoppingmall.core.web.dto.*;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping()
    public ResponseDto join(@Valid @RequestBody MemberCreateRequestDto user) throws Exception {
        return memberService.createMember(user);
    }
    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseDto deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }

    // 회원 수정
    @PutMapping("/{id}")
    public ResponseDto updateMember(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequestDto requestDto){
        return memberService.updateMember(id, requestDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseDto login(@Valid @RequestBody LoginRequestDto user) {
        return memberService.login(user);
    }

    // 회원 리스트 조회
    @GetMapping()
    public ResponseDto findMemberList() {
        return memberService.findMemberList();
    }

    // 회원 조회
    @GetMapping("/{id}")
    public ResponseDto findMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    // 사실 뭔지 잘 모르겠음. --------------------
//    @GetMapping("/user")
//    public ResponseDto userInfo(ServletRequest request) {
//
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        Member member = (Member) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));
//        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
//        return new ResponseDto("SUCCESS", memberResponseDto);
//    }
//
    @GetMapping("/auth/check")
    public ResponseDto authcheck(ServletRequest request) {
        return new ResponseDto("SUCCESS");
    }
    //------------------------------------
}

