package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.member.Member;
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
    public ResponseDto createOrder(@RequestBody OrderCreateRequestDto requestDto,  @AuthenticationPrincipal Member member) {
        return orderService.createOrder(member.getId(), requestDto);
    }

    @GetMapping()
    public ResponseDto findOrderList(@AuthenticationPrincipal Member member) {
        return orderService.findOrderList(member.getId());
    }

    @GetMapping("/{orderId}")
    public ResponseDto findOrderById(@AuthenticationPrincipal Member member, @PathVariable Long orderId) {
        return orderService.findOrderById(member.getId(), orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseDto updateOrder(@AuthenticationPrincipal Member member, @PathVariable Long orderId, @RequestBody OrderUpdateRequestDto requestDto) {
        return orderService.updateOrder(member.getId(), orderId, requestDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseDto deleteOrder(@AuthenticationPrincipal Member member, @PathVariable Long orderId) {
        return orderService.deleteOrder(member.getId(), orderId);
    }
}
