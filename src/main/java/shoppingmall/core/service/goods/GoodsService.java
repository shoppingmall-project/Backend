package shoppingmall.core.service.goods;

import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

public interface GoodsService {
    ResponseDto createGoods(GoodsCreateRequestDto requestDto, MultipartFile file, Long memberId) throws Exception;

    ResponseDto deleteGoods(Long goodsId, Long memberId) throws Exception;

    ResponseDto updateGoods(Long goodsId, GoodsUpdateRequestDto requestDto, MultipartFile file, Long memberId) throws Exception;

    ResponseDto findGoodsList();

    ResponseDto findGoodsById(Long goodsId);
}
