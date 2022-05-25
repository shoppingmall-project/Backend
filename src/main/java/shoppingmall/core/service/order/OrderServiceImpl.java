package shoppingmall.core.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.domain.order.Order;
import shoppingmall.core.domain.order.OrderRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    @Override
    public ResponseDto createOrder(Long memberId, Long goodsId, OrderCreateRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        Order order = requestDto.toEntity();
        order.setMember(member);
        order.setGoods(goods);

        Order savedOrder = orderRepository.save(order);

        OrderCreateResponseDto responseDto = OrderCreateResponseDto.builder()
                .id(savedOrder.getId())
                .build();
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteOrder(Long memberId, Long goodsId, Long orderId) {
        checkValidMemberAndGoods(memberId, goodsId);
        checkValidOrder(orderId);

        orderRepository.deleteById(orderId);
        return new ResponseDto("SUCCESS");
    }



    @Transactional
    @Override
    public ResponseDto updateOrder(Long memberId, Long goodsId, Long orderId, OrderUpdateRequestDto requestDto) {
        checkValidMemberAndGoods(memberId, goodsId);
        Order order = checkValidOrder(orderId);
        order.update(requestDto.getRequest(), requestDto.getPayment());

        OrderUpdateResponseDto responseDto = new OrderUpdateResponseDto(order.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }
    @Override
    public ResponseDto findOrderList(Long memberId, Long goodsId) {
        checkValidMemberAndGoods(memberId, goodsId);

        List<Order> orderList = orderRepository.findAllByMemberIdAndGoodsId(memberId, goodsId);

        List<OrderFindResponseDto> responseDtoList = new ArrayList<>();
        for(Order order : orderList) {
            OrderFindResponseDto responseDto = OrderFindResponseDto.toResponseDto(order);
            responseDtoList.add(responseDto);
        }

        return new ResponseDto("SUCCESS", responseDtoList);
    }

    @Override
    public ResponseDto findOrderById(Long memberId, Long goodsId, Long orderId) {
        checkValidMemberAndGoods(memberId, goodsId);
        Order order = checkValidOrder(orderId);

        OrderFindResponseDto responseDto = OrderFindResponseDto.toResponseDto(order);

        return new ResponseDto("SUCCESS", responseDto);
    }

    private Order checkValidOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당되는 주문이 없습니다"));
    }

    private void checkValidMemberAndGoods(Long memberId, Long goodsId) {
        memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        goodsRepository.findById(goodsId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }
}
