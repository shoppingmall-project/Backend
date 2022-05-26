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
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.domain.basket.Basket;
import shoppingmall.core.domain.basket.BasketRepository;
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

    @AfterEach
    void cleanup() {
        basketRepository.deleteAll();
        goodsRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("장바구니 추가")
    void crateBasket() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        int count = 5;


        //when
        String body = mapper.writeValueAsString(BasketCreateRequestDto.builder()
                .count(count)
                .build());

        //then
        mvc.perform(post("/member/"+member.getId()+"/goods/"+goods.getId()+"/basket")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertThat(basketRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("장바구니 삭제")
    void deleteBasket() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        int count = 4;

        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(count).build());

        //when
        mvc.perform(delete("/member/"+member.getId()+"/goods/"+goods.getId()+"/basket/"+basket.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(basketRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("장바구니 수정")
    void updateBasket() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(10)
                .build());

        int count = 4;


        //when
        String body = mapper.writeValueAsString(BasketUpdateReqeustDto.builder()
                .count(count)
                .build());

        mvc.perform(put("/member/"+member.getId()+"/goods/"+goods.getId()+"/basket/"+basket.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when
        assertThat(basketRepository.findAll()).isNotEmpty();
        assertThat(basket.getCount()).isEqualTo(4);

    }

    @Test
    @DisplayName("장바구니 리스트 조회")
    void findAllBasket() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

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

        mvc.perform(get("/member/"+member.getId()+"/goods/"+goods.getId()+"/basket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }


    @Test
    @DisplayName("장바구니 조회")
    void findBasketById() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Basket basket = basketRepository.save(Basket.builder()
                .member(member)
                .goods(goods)
                .count(1)
                .build());

        mvc.perform(get("/member/"+member.getId()+"/goods/"+goods.getId()+"/basket/"+basket.getId()))
                .andExpect(status().isOk());

        Assertions.assertThat(basketRepository.findById(basket.getId())).isNotEmpty();

    }
}
