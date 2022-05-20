package shoppingmall.core.service;

import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.ResponseDto;

public interface GoodsService {
    ResponseDto createGoods(GoodsCreateRequestDto requestDto);

    ResponseDto findGoods(Long id);

}
