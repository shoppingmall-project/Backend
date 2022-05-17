package shoppingmall.core.web.dto;

import lombok.Getter;
import shoppingmall.core.domain.Member;

@Getter
public class TokenDto {

    private String account;
    private String token;

    public TokenDto(Member entity, String token) {
        this.account = entity.getAccount();
        this.token = token;
    }
}
