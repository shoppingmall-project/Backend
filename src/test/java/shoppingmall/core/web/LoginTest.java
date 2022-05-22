//package shoppingmall.core.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import shoppingmall.core.domain.member.Member;
//import shoppingmall.core.domain.member.MemberRepository;
//import shoppingmall.core.service.login.MemberService;
//import shoppingmall.core.web.dto.LoginRequestDto;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class LoginTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//    MemberService memberService;
//
//    @Autowired
//    ObjectMapper mapper;
//
//    @Autowired
//    MockMvc mvc;
//
//    @BeforeEach
//    void createId() {
//        memberRepository.save(Member.builder()
//                .account("test")
//                .password("1234")
//                .gender("M")
//                .email("test@naver.com")
//                .name("test")
//                .role("Manager")
//                .build());
//    }
//    @AfterEach
//    void cleanUp() {
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("로그인")
//    void createMember() throws Exception {
//        //given
//        String account = "test";
//        String password = "1234";
//
//        //when
//        String body = mapper.writeValueAsString(LoginRequestDto.builder()
//                .account(account)
//                .password(password)
//                .build());
//
//        System.out.println("body = " + body);
//
//        //then
//        mvc.perform(post("/auth/login")
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(account));
//    }
//}
