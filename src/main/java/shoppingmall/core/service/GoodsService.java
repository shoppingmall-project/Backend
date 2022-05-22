package shoppingmall.core.service;

import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

public interface GoodsService {
    ResponseDto createGoods(GoodsCreateRequestDto requestDto);

    ResponseDto deleteGoods(Long id);

    ResponseDto updateGoods(Long id, GoodsUpdateRequestDto requestDto);

}
