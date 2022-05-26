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
import shoppingmall.core.domain.order.Order;
import shoppingmall.core.domain.order.OrderRepository;
import shoppingmall.core.web.dto.order.OrderCreateRequestDto;
import shoppingmall.core.web.dto.order.OrderUpdateRequestDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void cleanup() {
        orderRepository.deleteAll();
        goodsRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("주문 추가")
    void crateOrder() throws Exception {
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
                .price(50)
                .stock(50)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());


        //when
        String body = mapper.writeValueAsString(OrderCreateRequestDto.builder()
                .request("제품은 빠르게 배송해주세요")
                .payment(1)
                .build());

        //then
        mvc.perform(post("/member/"+member.getId()+"/goods/"+goods.getId()+"/order")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertThat(orderRepository.findAll()).isNotEmpty();
    }

    @Test
    @Transactional
    @DisplayName("주문 삭제")
    void deleteOrder() throws Exception {
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
                .price(50)
                .stock(50)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Order order = orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청사항")
                .payment(1)
                .build());

        //when
        mvc.perform(delete("/member/"+member.getId()+"/goods/"+goods.getId()+"/order/"+order.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(orderRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("주문 수정")
    void updateOrder() throws Exception {
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
                .price(50)
                .stock(50)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Order order = orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청사항")
                .payment(1)
                .build());

        //when
        String body = mapper.writeValueAsString(OrderUpdateRequestDto.builder()
                .request("수정사항")
                .payment(2)
                .build());

        mvc.perform(put("/member/"+member.getId()+"/goods/"+goods.getId()+"/order/"+order.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when
        assertThat(orderRepository.findAll()).isNotEmpty();
        assertThat(order.getPayment()).isEqualTo(2);
        assertThat(order.getRequest()).isEqualTo("수정사항");
    }

    @Test
    @DisplayName("주문 리스트 조회")
    void findAllOrder() throws Exception {
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
                .price(50)
                .stock(50)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청 사항")
                .payment(1)
                .build());
        orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청 사항 2")
                .payment(2)
                .build());

        mvc.perform(get("/member/"+member.getId()+"/goods/"+goods.getId()+"/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }


    @Test
    @DisplayName("주문 조회")
    void findOrderById() throws Exception {
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
                .price(50)
                .stock(50)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Order order = orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청 사항")
                .payment(2)
                .build());

        mvc.perform(get("/member/"+member.getId()+"/goods/"+goods.getId()+"/order/"+order.getId()))
                .andExpect(status().isOk());

        Assertions.assertThat(orderRepository.findById(order.getId())).isNotEmpty();

    }
}
