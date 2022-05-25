package shoppingmall.core.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.service.order.BasketService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.BasketCreateRequestDto;
import shoppingmall.core.web.dto.order.BasketUpdateReqeustDto;

@RestController
@RequestMapping("/member/{memberId}/goods/{goodsId}/basket")
public class OrderController {

    @Autowired
    BasketService basketService;

    //장바구니 생성
    @PostMapping()
    public ResponseDto createBasket(@PathVariable Long memberId, @PathVariable Long goodsId, @RequestBody BasketCreateRequestDto requestDto) {
        return basketService.createBasket(memberId, goodsId, requestDto);
    }

    //장바구니 리스트 조회
    @GetMapping()
    public ResponseDto findBasketList(@PathVariable Long memberId, @PathVariable Long goodsId) {
        return basketService.findBasketList(memberId, goodsId);
    }

    //장바구니 조회
    @GetMapping("/{basketId}")
    public ResponseDto findBaksetById(@PathVariable Long memberId, @PathVariable Long goodsId, @PathVariable Long basketId) {
        return basketService.findBasketById(memberId, goodsId, basketId);
    }

    //장바구니 수정
    @PutMapping("/{basketId}")
    public ResponseDto updateBasket(@PathVariable Long memberId, @PathVariable Long goodsId, @PathVariable Long basketId, @RequestBody BasketUpdateReqeustDto reqeustDto) {
        return basketService.updateBasket(memberId, goodsId, basketId, reqeustDto);
    }

    //장바구니 삭제
    @DeleteMapping("/{basketId}")
    public ResponseDto deleteBasket(@PathVariable Long memberId, @PathVariable Long goodsId, @PathVariable Long basketId) {
        return basketService.deleteBasket(memberId, goodsId, basketId);
    }
}
