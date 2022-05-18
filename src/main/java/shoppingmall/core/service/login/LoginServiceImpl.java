package shoppingmall.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.LoginRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    @Autowired
    MemberRepository memberRepository;

    /**
     * @return null이면 로그인 실패
     */
    @Override
    public Member login(String account, String password) {
        return memberRepository.findByAccount(account).stream()
                .filter(m -> m.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}

