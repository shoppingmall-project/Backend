package shoppingmall.core.service.order;

import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.order.BasketCreateRequestDto;
import shoppingmall.core.web.dto.order.BasketUpdateReqeustDto;

public interface BasketService {

    ResponseDto createBasket(Long memberId, Long goodsId, BasketCreateRequestDto requestDto);

    ResponseDto deleteBasket(Long memberId, Long goodsId, Long basketId);

    ResponseDto updateBasket(Long memberId, Long goodsId, Long basketId, BasketUpdateReqeustDto requestDto);

    ResponseDto findBasketList(Long memberId, Long goodsId);

    ResponseDto findBasketById(Long memberId, Long goodsId, Long basketId);


}
