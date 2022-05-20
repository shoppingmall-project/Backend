package shoppingmall.core.web.dto.member;

import lombok.Builder;

public class MemberCreateResponseDto {

    String account;

    @Builder
    public MemberCreateResponseDto(String account) {
        this.account = account;
    }
}
