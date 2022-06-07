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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.service.goods.GoodsService;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GoodsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void cleanup() {
        goodsRepository.deleteAll();
        memberRepository.deleteAll();
        session.clearAttributes();
    }

    protected MockHttpSession session;

    private void setSession(Member member) {
        session = new MockHttpSession();
        session.setAttribute("memberId", member.getId());
    }

    private Member getMember() {
        return memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("S")
                .email("test@naver.com")
                .name("test")
                .role("M")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());
    }

    @Test
    @DisplayName("상품 추가")
    void crateGoods() throws Exception {
        //given
        Member member = getMember();
        setSession(member);

        String category = "wine";
        String name = "test_wine";
        int price = 50000;
        int stock = 50;
        String description = "test용";
        String brand = "ASD";
        String country = "Korea";


        //when
        mvc.perform(post("/goods/")
                        .session(session)
                        .param("category", category)
                        .param("name", name)
                        .param("price", String.valueOf(price))
                        .param("stock", String.valueOf(stock))
                        .param("description", description)
                        .param("brand", brand)
                        .param("country", country))
                .andExpect(status().isOk());

        //then
        assertThat(goodsRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteGoods() throws Exception {
        //given
        Member member = getMember();
        setSession(member);

        String category = "wine";
        String name = "test_wine";
        int price = 50000;
        int stock = 50;
        String description = "test용";
        String brand = "ASD";
        String country = "Korea";

        Goods goods = goodsRepository.save(Goods.builder()
                .member(member)
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .build());

        //when
        mvc.perform(delete("/goods/" + goods.getId())
                        .session(session))
                .andExpect(status().isOk());

        //then
        assertThat(goodsRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("상품 수정")
    void updateGoods() throws Exception {
        Member member = getMember();
        setSession(member);

        //given
        String category = "wine";
        String name = "test_wine";
        int price = 50000;
        int stock = 50;
        String description = "test용";
        String brand = "ASD";
        String country = "Korea";

        Goods goods = goodsRepository.save(Goods.builder()
                .member(member)
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .imageUrl(null)
                .build());

        mvc.perform(put("/goods/" + goods.getId())
                        .session(session)
                        .param("category", "wine")
                        .param("name", "new_wine")
                        .param("price", String.valueOf(price))
                        .param("stock", String.valueOf(stock))
                        .param("description", description)
                        .param("brand", brand)
                        .param("country", country))
                .andExpect(status().isOk());
        //then
        assertThat(goods.getName()).isEqualTo("new_wine");
        assertThat(goods.getCategory()).isEqualTo("wine");

    }

    @Test
    @DisplayName("상품 리스트 조회")
    void findAllGoods() throws Exception {
        //given
        Member member = getMember();
        setSession(member);

        goodsRepository.save(Goods.builder()
                .member(member)
                .category("wine")
                .name("test1 wine")
                .price(50000)
                .stock(123)
                .description("조금 더 비싼 와인입니다")
                .brand("브랜드 1")
                .country("Korea")
                .build());

        goodsRepository.save(Goods.builder()
                .member(member)
                .category("wine")
                .name("test2 wine")
                .price(30000)
                .stock(234)
                .description("조금 더 싼 와인입니다.")
                .brand("브랜드 2")
                .country("Korea")
                .build());

        //when
        mvc.perform(get("/goods")
                        .session(session))
                .andExpect(status().isOk())

        //then
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }

    @Test
    @DisplayName("상품 조회")
    void findGoodsById() throws Exception {
        //given
        Member member = getMember();
        setSession(member);

        Goods goods = goodsRepository.save(Goods.builder()
                .member(member)
                .category("wine")
                .name("test2 wine")
                .price(30000)
                .stock(23)
                .description("조금 더 싼 와인입니다.")
                .brand("브랜드 2")
                .country("Korea")
                .build());

        //when
        mvc.perform(get("/goods/" + goods.getId())
                        .session(session))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(goodsRepository.findById(goods.getId())).isNotEmpty();

    }

}