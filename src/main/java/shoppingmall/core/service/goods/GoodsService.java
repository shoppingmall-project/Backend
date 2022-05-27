package shoppingmall.core.service.goods;

import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

public interface GoodsService {
    ResponseDto createGoods(GoodsCreateRequestDto requestDto, MultipartFile file) throws Exception;

    ResponseDto deleteGoods(Long id) throws Exception;

    ResponseDto updateGoods(Long id, GoodsUpdateRequestDto requestDto, MultipartFile file) throws Exception;

    ResponseDto findGoodsList();

    ResponseDto findGoodsById(Long id);
}
