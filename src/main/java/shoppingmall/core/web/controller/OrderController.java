package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.order.OrderService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.OrderCreateRequestDto;
import shoppingmall.core.web.dto.order.OrderUpdateRequestDto;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping()
    public ResponseDto createOrder(@RequestBody OrderCreateRequestDto requestDto,  HttpSession session) {
        Long memberId = createSession(session);
        return orderService.createOrder(memberId, requestDto);
    }

    @GetMapping()
    public ResponseDto findOrderList(HttpSession session) {
        Long memberId = createSession(session);
        return orderService.findOrderList(memberId);
    }

    @GetMapping("/{orderId}")
    public ResponseDto findOrderById(HttpSession session, @PathVariable Long orderId) {
        Long memberId = createSession(session);
        return orderService.findOrderById(memberId, orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseDto updateOrder(HttpSession session, @PathVariable Long orderId, @RequestBody OrderUpdateRequestDto requestDto) {
        Long memberId = createSession(session);
        return orderService.updateOrder(memberId, orderId, requestDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseDto deleteOrder(HttpSession session, @PathVariable Long orderId) {
        Long memberId = createSession(session);
        return orderService.deleteOrder(memberId, orderId);
    }

    private Long createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        return (Long) session.getAttribute("memberId");
    }
}
