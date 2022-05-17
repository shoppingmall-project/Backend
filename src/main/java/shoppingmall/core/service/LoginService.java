package shoppingmall.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.Member;
import shoppingmall.core.domain.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    /**
     * @return null이면 로그인 실패
     */
    public Member login(String account, String password) {
        return memberRepository.findByAccount(account).stream()
                .filter(m -> m.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}

