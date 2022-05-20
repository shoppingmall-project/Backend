package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.login.MemberService;

import shoppingmall.core.web.dto.member.MemberCreateRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void memberSignup() throws Exception {
        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        //when
        String body = mapper.writeValueAsString(MemberCreateRequestDto.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build()
        );

        //then
        mvc.perform(post("/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(account));
    }
}

