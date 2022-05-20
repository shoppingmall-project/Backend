package shoppingmall.core.web.dto.member;

import lombok.Builder;

public class MemberUpdateResponseDto {


    String account;

    @Builder
    public MemberUpdateResponseDto(String account) {
        this.account = account;
    }
}
