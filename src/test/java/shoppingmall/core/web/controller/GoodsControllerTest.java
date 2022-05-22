package shoppingmall.core.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import shoppingmall.core.service.GoodsService;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GoodsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    GoodsRepository goodsRepository;
    GoodsService goodsService;

    @AfterEach
    void cleanup() {
        goodsRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 추가")
    void crateGoods() throws Exception {
        //given
        String category = "wine";
        String name = "test_wine";
        String price = "50000";
        String stock = "50";
        String description = "test용";
        String brand = "ASD";
        String country = "Korea";

        //when
        String body = mapper.writeValueAsString(GoodsCreateRequestDto.builder()
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .build()
        );

        //then
        mvc.perform(post("/goods/create")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertThat(!goodsRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteGoods() throws Exception {
        //given
        String category = "wine";
        String name = "test_wine";
        String price = "50000";
        String stock = "50";
        String description = "test용";
        String brand = "ASD";
        String country = "Korea";

        Goods goods = goodsRepository.save(Goods.builder()
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .build());

        //when
        mvc.perform(delete("/goods/" + goods.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(goodsRepository.findAll().isEmpty());
    }

    @Transactional
    @Test
    @DisplayName("상품 수정")
    void updateGoods() throws Exception {
        //given
        String category = "wine";
        String name = "test_wine";
        String price = "50000";
        String stock = "50";
        String description = "test용";
        String brand = "ASD";
        String country = "Korea";

        Goods goods = goodsRepository.save(Goods.builder()
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .build());


        //when
        String body = mapper.writeValueAsString(GoodsUpdateRequestDto.builder()
                .category("wine")
                .name("new_wine")
                .price(price)
                .stock(stock)
                .description(description)
                .brand(brand)
                .country(country)
                .build()
        );

        mvc.perform(put("/goods/" + goods.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when
        System.out.println("goods = "+ goods.getName());
        assertThat(!goodsRepository.findAll().isEmpty());
        assertThat(goods.getName()).isEqualTo("new_wine");

    }

    @Test
    @DisplayName("상품 리스트 조회")
    void findAllGoods() throws Exception {

        goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test1 wine")
                .price("50000")
                .stock("123")
                .description("조금 더 비싼 와인입니다")
                .brand("브랜드 1")
                .country("Korea")
                .build());

        goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test2 wine")
                .price("30000")
                .stock("234")
                .description("조금 더 싼 와인입니다.")
                .brand("브랜드 2")
                .country("Korea")
                .build());

        mvc.perform(get("/goodslist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test1 wine"))
                .andExpect(jsonPath("$[1].name").value("test2 wine"));


    }
}
