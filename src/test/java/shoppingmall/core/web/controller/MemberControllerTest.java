package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.member.MemberService;

import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;
    MemberService memberService;

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
    void createMember() throws Exception {
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

    @Test
    @DisplayName("회원 탈퇴")
    void deleteMember() throws Exception {
        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        memberRepository.save(Member.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build());

        //when
        mvc.perform(delete("/auth/" + account))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(memberRepository.findByAccount(account).isEmpty());
//    }
    }

    @Test
    @Transactional
    @DisplayName("회원 정보 수정")
    void updateMember() throws Exception {

        //given
        String account = "test";
        String password = "1234";
        String gender = "M";
        String email = "test@naver.com";
        String name = "test";
        String role = "Manager";

        String new_email = "new_test@naver.com";
        String new_password = "123";
        String new_role = "User";

        memberRepository.save(Member.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build());

        //when
        String body = mapper.writeValueAsString(MemberUpdateRequestDto.builder()
                .password(new_password)
                .role(new_role)
                .email(new_email)
                .build()
        );

        //when
        mvc.perform(put("/auth/"+ account)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
        //then
                .andExpect(status().isOk());
        Assertions.assertThat(memberRepository.findByAccount(account)).isPresent();


    }
}

