package shoppingmall.core.web.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateResponseDto {

    Long id;
    String account;

    public MemberCreateResponseDto(Long id, String account) {
        this.id = id;
        this.account = account;
    }
}
