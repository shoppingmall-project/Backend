package shoppingmall.core.service.order;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.OrderCreateRequestDto;
import shoppingmall.core.web.dto.order.OrderUpdateRequestDto;

public interface OrderService {

    ResponseDto createOrder(Long memberId, OrderCreateRequestDto requestDto);

    ResponseDto deleteOrder(Long memberId, Long orderId);

    ResponseDto updateOrder(Long memberId, Long orderId, OrderUpdateRequestDto requestDto);

    ResponseDto findOrderList(Long memberId);

    ResponseDto findOrderById(Long memberId, Long orderId);
}
