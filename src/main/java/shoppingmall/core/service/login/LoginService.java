package shoppingmall.core.service.login;

import shoppingmall.core.domain.member.Member;

public interface LoginService {
    Member login(String account, String password);
}
