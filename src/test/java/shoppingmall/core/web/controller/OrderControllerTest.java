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
import shoppingmall.core.domain.order.Order;
import shoppingmall.core.domain.order.OrderRepository;
import shoppingmall.core.web.dto.LoginRequestDto;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanup() {
        orderRepository.deleteAll();
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
                .role("Manager")
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
    @Transactional
    @DisplayName("주문 추가")
    void crateOrder() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);

        //when
        String body = mapper.writeValueAsString(OrderCreateRequestDto.builder()
                .goods_id(goods.getId())
                .request("제품은 빠르게 배송해주세요")
                .payment(1)
                .build());

        //then
        mvc.perform(post("/order")
                        .header("X-AUTH-TOKEN", getAccessToken())
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
        Member member = getMember();
        Goods goods = getGoods(member);
        Order order = orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청사항")
                .payment(1)
                .build());

        //when
        mvc.perform(delete("/order/"+order.getId())
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk());

        //then
        assertThat(orderRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("주문 수정")
    void updateOrder() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
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

        mvc.perform(put("/order/"+order.getId())
                        .header("X-AUTH-TOKEN", getAccessToken())
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
        Member member = getMember();
        Goods goods = getGoods(member);

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

        //when
        mvc.perform(get("/order")
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk())

        //then
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }


    @Test
    @DisplayName("주문 조회")
    void findOrderById() throws Exception {
        //given
        Member member = getMember();
        Goods goods = getGoods(member);
        Order order = orderRepository.save(Order.builder()
                .member(member)
                .goods(goods)
                .request("요청 사항")
                .payment(2)
                .build());

        //when
        mvc.perform(get("/order/"+order.getId()))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(orderRepository.findById(order.getId())).isNotEmpty();

    }
}
