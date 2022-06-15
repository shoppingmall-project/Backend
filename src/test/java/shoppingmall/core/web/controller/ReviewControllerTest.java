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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.domain.review.Review;
import shoppingmall.core.domain.review.ReviewRepository;
import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.review.ReviewUpdateRequestDto;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanup() {
        goodsRepository.deleteAll();
        reviewRepository.deleteAll();
        memberRepository.deleteAll();
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

    private Goods getGoods(Member member) {
        return goodsRepository.save(Goods.builder()
                .member(member)
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        String content = "asdasdasd";

        //when
        mvc.perform(post("/goods/" + goods.getId() + "/review")
                        .header("X-AUTH-TOKEN", getAccessToken())
                        .param("content", content))
                .andExpect(status().isOk());

        //then
        assertThat(goodsRepository.findAll()).isNotEmpty();
        assertThat(reviewRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReview() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        String content = "asdasdasd";
        Review review = reviewRepository.save(Review.builder()
                .member(member)
                .goods(goods)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/goods/" +goods.getId()+ "/review/" +review.getId())
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk());

        //then
        assertThat(reviewRepository.findAll()).isEmpty();
        assertThat(goodsRepository.findAll()).isNotEmpty();
    }

    @Transactional
    @Test
    @DisplayName("리뷰 수정")
    void updateReview() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        Review review = reviewRepository.save(Review.builder()
                .member(member)
                .goods(goods)
                .content("asdasdasd")
                .build());

        //when
        String body = mapper.writeValueAsString(ReviewUpdateRequestDto.builder()
                .content("new_content")
                .build()
        );

        mvc.perform(put("/goods/" + goods.getId() + "/review/" + review.getId())
                        .header("X-AUTH-TOKEN", getAccessToken())
                        .param("content", "new_content"))
                .andExpect(status().isOk());

        //then
        assertThat(reviewRepository.findAll()).isNotEmpty();
        assertThat(review.getContent()).isEqualTo("new_content");
    }

    @Test
    @DisplayName("리뷰 리스트 조회")
    void findReviewList() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);

        reviewRepository.save(Review.builder()
                .member(member)
                .goods(goods)
                .content("content")
                .build());

        reviewRepository.save(Review.builder()
                .member(member)
                .goods(goods)
                .content("content2")
                .build());

        mvc.perform(get("/goods/" +goods.getId() +"/review"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));
    }

    @Test
    @DisplayName("리뷰 조회")
    void findReviewById() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        Review review = reviewRepository.save(Review.builder()
                .member(member)
                .goods(goods)
                .content("content")
                .build());

        mvc.perform(get("/goods/" + goods.getId() + "/review/" + review.getId()))
                .andExpect(status().isOk());

        Assertions.assertThat(reviewRepository.findById(review.getId())).isNotEmpty();
    }

    @Test
    @DisplayName("게시글 삭제시 리뷰 삭제 여부")
    void deleteReviewWhenDeletegoods() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        String content = "asdasdasd";
        reviewRepository.save(Review.builder()
                .member(member)
                .goods(goods)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/goods/" +goods.getId())
                        .header("X-AUTH-TOKEN", getAccessToken())
                )
                .andExpect(status().isOk());

        //then
        assertThat(reviewRepository.findAll()).isEmpty();
        assertThat(goodsRepository.findAll()).isEmpty();

    }

}
