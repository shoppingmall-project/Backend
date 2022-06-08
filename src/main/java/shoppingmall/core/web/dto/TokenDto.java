package shoppingmall.core.web.dto;

import lombok.*;
import shoppingmall.core.domain.member.Member;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {

    private Long id;
    private String account;
    private String token;

    public TokenDto(Member entity, String token) {
        this.id = entity.getId();
        this.account = entity.getAccount();
        this.token = token;
    }
}
