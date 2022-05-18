//package shoppingmall.core.web.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import shoppingmall.core.domain.member.Member;
//import shoppingmall.core.domain.member.MemberRepository;
//import shoppingmall.core.web.dto.*;
//
//import java.util.Collections;
//import java.util.Random;
//
//import static org.assertj.core.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class MemberApiControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @AfterEach
//    void cleanUp() {
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("회원가입")
//    void memberSignup() {
//        //given
//        String account = "test";
//        String password = "1234";
//        String gender = "M";
//        String email = "test@naver.com";
//        String name = "test";
//        String role = "Manager";
//
//        MemberCreateRequestDto requestDto = MemberCreateRequestDto.builder()
//                .account(account)
//                .password(password)
//                .gender(gender)
//                .email(email)
//                .name(name)
//                .role(role)
//                .build();
//
//        String url = "http://localhost:" + port + "/signup";
//
//        System.out.println("account = " + account);
//        System.out.println("password = " + password);
//        System.out.println("memberRepository = " + memberRepository);
//
//        //when
//        ResponseEntity<ResponseDto> responseEntity
//                = restTemplate.postForEntity(url, requestDto, ResponseDto.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        //responseData
//        Object responseData = responseEntity.getBody().getData();
//
//        ObjectMapper mapper = new ObjectMapper();
//        MemberResponseDto responseDto = mapper.convertValue(responseData, MemberResponseDto.class);
//
//        Member newmember = memberRepository.findByAccount(responseDto.getAccount()).orElseThrow();
//
//        assertThat(newmember.getAccount()).isEqualTo(account);
//        assertThat(newmember.getPassword()).isEqualTo(password);
//    }
//}
