package shoppingmall.core.service.basket;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.basket.BasketCreateRequestDto;
import shoppingmall.core.web.dto.basket.BasketUpdateReqeustDto;

public interface BasketService {

    ResponseDto createBasket(BasketCreateRequestDto requestDto);

    ResponseDto deleteBasket(Long basketId);

    ResponseDto updateBasket(Long basketId, BasketUpdateReqeustDto requestDto);

    ResponseDto findBasketList(Long memberId);

    ResponseDto findBasketById(Long basketId);


}
