package shoppingmall.core.service.member;

import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;


public interface MemberService {
    ResponseDto login(LoginRequestDto user);

    ResponseDto deleteMember(Long id);

    ResponseDto updateMember(Long id, MemberUpdateRequestDto requestDto);

    String createMember(MemberCreateRequestDto requestDto) throws Exception;

    ResponseDto findMemberList();

    ResponseDto findMemberById(Long id);
}



