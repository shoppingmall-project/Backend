package shoppingmall.core.service.order;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.OrderCreateRequestDto;
import shoppingmall.core.web.dto.order.OrderUpdateRequestDto;

public interface OrderService {

    ResponseDto createOrder(Long memberId, Long goodsId, OrderCreateRequestDto requestDto);

    ResponseDto deleteOrder(Long memberId, Long goodsId, Long orderId);

    ResponseDto updateOrder(Long memberId, Long goodsId, Long orderId, OrderUpdateRequestDto requestDto);

    ResponseDto findOrderList(Long memberId, Long goodsId);

    ResponseDto findOrderById(Long memberId, Long goodsId, Long orderId);
}
