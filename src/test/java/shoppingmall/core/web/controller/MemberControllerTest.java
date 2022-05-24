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
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.member.MemberService;

import shoppingmall.core.web.dto.member.MemberCreateRequestDto;
import shoppingmall.core.web.dto.member.MemberUpdateRequestDto;

import javax.transaction.Transactional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mvc.perform(post("/auth")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Assertions.assertThat(memberRepository.findAll()).isNotEmpty();

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

        Member member = memberRepository.save(Member.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .build());

        //when
        mvc.perform(delete("/auth/" + member.getId()))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(memberRepository.findById(member.getId())).isEmpty();
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

        Member member = memberRepository.save(Member.builder()
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
        mvc.perform(put("/auth/" + member.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
        //then
                .andExpect(status().isOk());
        Assertions.assertThat(memberRepository.findById(member.getId())).isNotEmpty();
        Assertions.assertThat(member.getEmail()).isEqualTo(new_email);
    }

    @Test
    @DisplayName("멤버 리스트 조회")
    void findAllGoods() throws Exception {

        memberRepository.save(Member.builder()
                .name("박민우")
                .role("M")
                .email("alsdndia789@naver.com")
                .gender("M")
                .password("1234")
                .account("test")
                .build());

        memberRepository.save(Member.builder()
                .name("최영원")
                .role("M")
                .email("choi@naver.com")
                .gender("M")
                .password("5678")
                .account("test2")
                .build());

        mvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }
}

