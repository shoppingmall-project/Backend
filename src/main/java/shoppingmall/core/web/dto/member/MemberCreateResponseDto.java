package shoppingmall.core.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateResponseDto {

    Long id;
    String account;

    @Builder
    public MemberCreateResponseDto(Long id, String account) {
        this.id = id;
        this.account = account;
    }
}
