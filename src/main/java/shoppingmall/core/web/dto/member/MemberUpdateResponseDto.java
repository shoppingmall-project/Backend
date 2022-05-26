package shoppingmall.core.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateResponseDto {

    String account;

    @Builder
    public MemberUpdateResponseDto(String account) {
        this.account = account;
    }
}
