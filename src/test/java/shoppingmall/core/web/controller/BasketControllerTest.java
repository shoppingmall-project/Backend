package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import shoppingmall.core.domain.basket.Basket;
import shoppingmall.core.domain.basket.BasketRepository;
import shoppingmall.core.service.member.MemberService;
import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.basket.BasketCreateRequestDto;
import shoppingmall.core.web.dto.basket.BasketUpdateReqeustDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTest {


    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @AfterEach
    void cleanup() {
        basketRepository.deleteAll();
        goodsRepository.deleteAll();
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
                .address("????????????")
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
                .description("Test???")
                .brand("ASD")
                .country("Korea")
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
        System.out.println("jsonArr = " + jsonArr);
        return (String) jsonArr.get("token");
    }

    @Test
    @DisplayName("???????????? ??????")
    void crateBasket() throws Exception {
        //given
        Member member = getMember();

        Goods goods = getGoods(member);

        //when
        String body = mapper.writeValueAsString(BasketCreateRequestDto.builder()
                .goods_id(goods.getId())
                .count(5)
                .build());

        mvc.perform(post("/basket")
                        .header("X-AUTH-TOKEN", getAccessToken())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("???????????? ??????")
    void deleteBasket() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);

        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(4).build());

        //when
        mvc.perform(delete("/basket/"+basket.getId())
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("???????????? ??????")
    void updateBasket() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);

        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(10)
                .build());

        //when
        String body = mapper.writeValueAsString(BasketUpdateReqeustDto.builder()
                .count(4)
                .build());

        mvc.perform(put("/basket/"+basket.getId())
                        .header("X-AUTH-TOKEN", getAccessToken())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isNotEmpty();
        assertThat(basket.getCount()).isEqualTo(4);

    }

    @Test
    @DisplayName("???????????? ????????? ??????")
    void findAllBasket() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);

        basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(1)
                .build());

        basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(5)
                .build());

        //when
        mvc.perform(get("/basket")
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk())

        //then
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }


    @Test
    @DisplayName("???????????? ??????")
    void findBasketById() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(1)
                .build());

        //when
        mvc.perform(get("/basket/"+basket.getId()))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(basketRepository.findById(basket.getId())).isNotEmpty();

    }
}
