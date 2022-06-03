package shoppingmall.core.service.member;

import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;

import javax.servlet.http.HttpServletRequest;


public interface MemberService {
    ResponseDto login(LoginRequestDto user, HttpServletRequest request);

    ResponseDto logout(HttpServletRequest request);

    ResponseDto deleteMember(Long id);

    ResponseDto updateMember(Long id, MemberUpdateRequestDto requestDto);

    ResponseDto createMember(MemberCreateRequestDto requestDto) throws Exception;

    ResponseDto findMemberList();

    ResponseDto findMemberById(Long id);

}



