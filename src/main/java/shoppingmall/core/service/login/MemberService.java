package shoppingmall.core.service.login;

import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.member.MemberCreateRequestDto;


public interface MemberService {
    ResponseDto login(LoginRequestDto user);

    ResponseDto deleteMember(Long id);

    Long createMember(MemberCreateRequestDto requestDto) throws Exception;
}



