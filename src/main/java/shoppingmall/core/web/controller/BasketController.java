package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.basket.BasketService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.basket.BasketCreateRequestDto;
import shoppingmall.core.web.dto.basket.BasketUpdateReqeustDto;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    BasketService basketService;

    //장바구니 생성
    @PostMapping()
    public ResponseDto createBasket(@RequestBody BasketCreateRequestDto requestDto, HttpSession session) {
        Long memberId = createSession(session);
        return basketService.createBasket(requestDto, memberId);
    }

    //장바구니 리스트 조회
    @GetMapping()
    public ResponseDto findBasketList(HttpSession session) {
        Long memberId = createSession(session);
        return basketService.findBasketList(memberId);
    }

    //장바구니 조회
    @GetMapping("/{basketId}")
    public ResponseDto findBasketById(@PathVariable Long basketId) {
        return basketService.findBasketById(basketId);
    }

    //장바구니 수정
    @PutMapping("/{basketId}")
    public ResponseDto updateBasket(@PathVariable Long basketId, @RequestBody BasketUpdateReqeustDto requestDto) {
        return basketService.updateBasket(basketId, requestDto);
    }

    //장바구니 삭제
    @DeleteMapping("/{basketId}")
    public ResponseDto deleteBasket(@PathVariable Long basketId) {
        return basketService.deleteBasket(basketId);
    }

    private Long createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        return (Long) session.getAttribute("memberId");
    }
}
