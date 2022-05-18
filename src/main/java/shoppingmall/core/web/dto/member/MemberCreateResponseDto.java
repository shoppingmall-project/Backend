package shoppingmall.core.web.dto.member;

import lombok.Builder;

public class MemberCreateResponseDto {

    Long memberId;

    @Builder
    public MemberCreateResponseDto(Long memberId) {
        this.memberId = memberId;
    }
}
