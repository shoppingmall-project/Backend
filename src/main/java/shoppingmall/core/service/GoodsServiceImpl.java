package shoppingmall.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsCreateResponseDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsFindResponseDto;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsRepository goodsRepository;

    @Override
    public ResponseDto createGoods(GoodsCreateRequestDto requestDto) {
        Goods goods = goodsRepository.save(requestDto.toEntity());
        GoodsCreateResponseDto responseDto = new GoodsCreateResponseDto(goods.getId());
        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findGoods(Long id) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. "));
        GoodsFindResponseDto responseDto = GoodsFindResponseDto.toResponseDto(goods);

        return new ResponseDto("SUCCESS", responseDto);

    }


}
