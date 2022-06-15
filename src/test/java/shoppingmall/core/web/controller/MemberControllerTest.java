package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.member.MemberService;

import shoppingmall.core.web.dto.LoginRequestDto;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    private Member getMember() {
        return memberRepository.save(Member.builder()
                .account("test")
                .password(passwordEncoder.encode("1234"))
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("M")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());
    }

    private String getAccessToken() throws Exception {
        String username = "test";
        String password = "1234";

        String body = mapper.writeValueAsString(LoginRequestDto.builder()
                .account(username)
                .password(password)
                .build()
        );

        ResultActions perform = this.mvc.perform(post("/auth/login")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject)jsonParser.parse(responseBody);
        JSONObject jsonArr = (JSONObject) jsonObj.get("data");
        return (String) jsonArr.get("token");
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
        String address = "개포로";
        String phoneNum = "010231312341";

        //when
        String body = mapper.writeValueAsString(MemberCreateRequestDto.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .address(address)
                .phoneNum(phoneNum)
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
        String address = "개포로";
        String phoneNum = "010231312341";

        Member member = memberRepository.save(Member.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .address(address)
                .phoneNum(phoneNum)
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
        String address = "개포로";
        String phoneNum = "010231312341";

        String new_email = "new_test@naver.com";
        String new_password = "123";
        String new_role = "User";
        String new_address = "개포로 110길";
        String new_phoneNum = "01026844510";

        Member member = memberRepository.save(Member.builder()
                .account(account)
                .password(password)
                .gender(gender)
                .email(email)
                .name(name)
                .role(role)
                .address(address)
                .phoneNum(phoneNum)
                .build());

        //when
        String body = mapper.writeValueAsString(MemberUpdateRequestDto.builder()
                .password(new_password)
                .role(new_role)
                .email(new_email)
                .address(new_address)
                .phoneNum(new_phoneNum)
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
        Assertions.assertThat(member.getPhoneNum()).isEqualTo(new_phoneNum);
    }

    @Test
    @DisplayName("회원 리스트 조회")
    void findAllMember() throws Exception {
        Member member = getMember();

        memberRepository.save(Member.builder()
                .name("박민우")
                .role("M")
                .email("alsdndia789@naver.com")
                .gender("M")
                .password("1234")
                .account("test123")
                .address("개포로110길")
                .phoneNum("01026844510")
                .build());

        memberRepository.save(Member.builder()
                .name("최영원")
                .role("M")
                .email("choi@naver.com")
                .gender("M")
                .password("5678")
                .account("test2")
                .address("개포로110길")
                .phoneNum("01026844510")
                .build());

        mvc.perform(get("/auth")
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(3)));

    }

    @Test
    @DisplayName("회원 조회")
    void findMemberById() throws Exception {

        Member member = memberRepository.save(Member.builder()
                .name("박민우")
                .role("M")
                .email("alsdndia789@naver.com")
                .gender("M")
                .password("1234")
                .account("test")
                .address("개포로110길")
                .phoneNum("01026844510")
                .build());

        mvc.perform(get("/auth/" + member.getId()))
                .andExpect(status().isOk());

        Assertions.assertThat(memberRepository.findById(member.getId())).isNotEmpty();

    }
}

