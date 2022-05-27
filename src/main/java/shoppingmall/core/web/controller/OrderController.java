package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.order.OrderService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.OrderCreateRequestDto;
import shoppingmall.core.web.dto.order.OrderUpdateRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/{memberId}/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping()
    public ResponseDto createOrder(@PathVariable Long memberId, @RequestBody OrderCreateRequestDto requestDto) {
        return orderService.createOrder(memberId, requestDto);
    }

    @GetMapping()
    public ResponseDto findOrderList(@PathVariable Long memberId) {
        return orderService.findOrderList(memberId);
    }

    @GetMapping("/{orderId}")
    public ResponseDto findOrderById(@PathVariable Long memberId, @PathVariable Long orderId) {
        return orderService.findOrderById(memberId, orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseDto updateOrder(@PathVariable Long memberId, @PathVariable Long orderId, @RequestBody OrderUpdateRequestDto requestDto) {
        return orderService.updateOrder(memberId, orderId, requestDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseDto deleteOrder(@PathVariable Long memberId, @PathVariable Long orderId) {
        return orderService.deleteOrder(memberId, orderId);
    }
}
