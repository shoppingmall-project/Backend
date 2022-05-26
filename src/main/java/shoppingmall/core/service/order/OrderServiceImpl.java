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
        Member member = checkValidMember(memberId);
        Goods goods = checkValidGoods(goodsId);

        Order order = requestDto.toEntity();
        order.setMemberAndGoods(member,goods);

        Order savedOrder = orderRepository.save(order);

        OrderCreateResponseDto responseDto = new OrderCreateResponseDto(savedOrder.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }


    @Transactional
    @Override
    public ResponseDto deleteOrder(Long memberId, Long goodsId, Long orderId) {
        checkValidMember(memberId);
        checkValidGoods(goodsId);
        checkValidOrder(orderId);

        orderRepository.deleteById(orderId);
        return new ResponseDto("SUCCESS");
    }

    @Transactional
    @Override
    public ResponseDto updateOrder(Long memberId, Long goodsId, Long orderId, OrderUpdateRequestDto requestDto) {
        checkValidMember(memberId);
        checkValidGoods(goodsId);
        Order order = checkValidOrder(orderId);
        order.update(requestDto.getRequest(), requestDto.getPayment());

        OrderUpdateResponseDto responseDto = new OrderUpdateResponseDto(order.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }
    @Override
    public ResponseDto findOrderList(Long memberId, Long goodsId) {
        checkValidMember(memberId);
        checkValidGoods(goodsId);

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
        checkValidMember(memberId);
        checkValidGoods(goodsId);
        Order order = checkValidOrder(orderId);

        OrderFindResponseDto responseDto = OrderFindResponseDto.toResponseDto(order);

        return new ResponseDto("SUCCESS", responseDto);
    }

    private Order checkValidOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당되는 주문이 없습니다"));
    }

    private Goods checkValidGoods(Long goodsId) {
        return goodsRepository.findById(goodsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }

    private Member checkValidMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }
}
