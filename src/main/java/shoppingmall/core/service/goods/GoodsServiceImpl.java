package shoppingmall.core.service.goods;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsCreateResponseDto;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.transaction.Transactional;
import java.util.List;

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
    public ResponseDto deleteGoods(Long id) {
        goodsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        goodsRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
    }

    @Override
    @Transactional
    public ResponseDto updateGoods(Long id, GoodsUpdateRequestDto requestDto) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다"));
        goods.updateGoods(requestDto.getCategory(), requestDto.getName(), requestDto.getPrice(), requestDto.getStock(), requestDto.getDescription(), requestDto.getBrand(), requestDto.getCountry());

        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto findGoodsList() {
        List<Goods> goodsList = goodsRepository.findGoodsList();
        return new ResponseDto("SUCCESS", goodsList);
    }

    @Override
    public ResponseDto findGoodsById(Long id) {
        goodsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다"));
        Goods goods = goodsRepository.findGoodsById(id);

        return new ResponseDto("SUCCESS", goods);
    }
}
