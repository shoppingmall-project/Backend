package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.domain.basket.Basket;
import shoppingmall.core.domain.basket.BasketRepository;
import shoppingmall.core.service.member.MemberService;
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

    protected MockHttpSession session;

    @AfterEach
    void cleanup() {
        basketRepository.deleteAll();
        goodsRepository.deleteAll();
        memberRepository.deleteAll();
        session.clearAttributes();
    }

    private void setSession(Member member) {
        session = new MockHttpSession();
        session.setAttribute("memberId", member.getId());
    }

    private Member getMember() {
        return memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
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
    @DisplayName("장바구니 추가")
    void crateBasket() throws Exception {
        //given
        Member member = getMember();
        setSession(member);
        Goods goods = getGoods(member);

        //when
        String body = mapper.writeValueAsString(BasketCreateRequestDto.builder()
                .goods_id(goods.getId())
                .count(5)
                .build());

        mvc.perform(post("/basket")
                        .session(session)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("장바구니 삭제")
    void deleteBasket() throws Exception {
        //given
        Member member = getMember();
        setSession(member);
        Goods goods = getGoods(member);

        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(4).build());

        //when
        mvc.perform(delete("/basket/"+basket.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("장바구니 수정")
    void updateBasket() throws Exception {
        //given
        Member member = getMember();
        setSession(member);
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
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isNotEmpty();
        assertThat(basket.getCount()).isEqualTo(4);

    }

    @Test
    @DisplayName("장바구니 리스트 조회")
    void findAllBasket() throws Exception {
        //given
        Member member = getMember();
        setSession(member);
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
                        .session(session))
                .andExpect(status().isOk())

        //then
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }


    @Test
    @DisplayName("장바구니 조회")
    void findBasketById() throws Exception {
        //given
        Member member = getMember();
        setSession(member);
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
