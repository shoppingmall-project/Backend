package shoppingmall.core.service.basket;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.basket.BasketCreateRequestDto;
import shoppingmall.core.web.dto.basket.BasketUpdateReqeustDto;

public interface BasketService {

    ResponseDto createBasket(Long memberId, BasketCreateRequestDto requestDto);

    ResponseDto deleteBasket(Long memberId, Long basketId);

    ResponseDto updateBasket(Long memberId, Long basketId, BasketUpdateReqeustDto requestDto);

    ResponseDto findBasketList(Long memberId);

    ResponseDto findBasketById(Long memberId, Long basketId);


}
