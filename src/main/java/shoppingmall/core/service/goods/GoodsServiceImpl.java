package shoppingmall.core.service.goods;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.review.Review;
import shoppingmall.core.service.storage.StorageService;
import shoppingmall.core.web.dto.goods.*;
import shoppingmall.core.web.dto.ResponseDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    @Qualifier("FileStorageService")
    private final GoodsRepository goodsRepository;
    private final StorageService storageService;

    @Transactional
    @Override
    public ResponseDto createGoods(GoodsCreateRequestDto requestDto, MultipartFile file) throws Exception {

        Goods goods = goodsRepository.save(requestDto.toEntity());

        if(file != null) {
            saveFileAndUrl(file, goods);
        }
        GoodsCreateResponseDto responseDto = new GoodsCreateResponseDto(goods.getId());
        return new ResponseDto("SUCCESS", responseDto);

    }

    @Transactional
    @Override
    public ResponseDto deleteGoods(Long id) throws Exception{
        checkValidGoods(id);
        Goods goods = checkValidGoods(id);

        if(goods.getImageUrl() != null) {
            if (storageService.delete(goods.getImageUrl())) {
                goods.updateUrl(null);
            }
        }

        goodsRepository.deleteById(id);

        return new ResponseDto("SUCCESS");
    }

    @Override
    @Transactional
    public ResponseDto updateGoods(Long id, GoodsUpdateRequestDto requestDto, MultipartFile file) throws Exception {
        Goods goods = checkValidGoods(id);
        goods.updateGoods(requestDto.getCategory(), requestDto.getName(), requestDto.getPrice(), requestDto.getStock(),
                requestDto.getDescription(), requestDto.getBrand(), requestDto.getCountry());

        // 원래 이미지가 있다면
        System.out.println("goods.getImageUrl() = " + goods.getImageUrl());
        if(goods.getImageUrl() != null) {
            if (storageService.delete(goods.getImageUrl())) {
                goods.updateUrl(null);
            }
        }
        // 수정할 파일이 있다면
        if(file != null) {
            saveFileAndUrl(file, goods);
        }
        GoodsUpdateResponseDto responseDto = new GoodsUpdateResponseDto(goods.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findGoodsList() {
        List<Goods> goodsList = goodsRepository.findAll();
        List<GoodsFindResponseDto> responseDtoList = new ArrayList<>();

        for(Goods goods : goodsList) {
            GoodsFindResponseDto responseDto = GoodsFindResponseDto.toResponseDto(goods);

            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }

    @Override
    public ResponseDto findGoodsById(Long id) {
        Goods goods = checkValidGoods(id);
        GoodsFindResponseDto responseDto = GoodsFindResponseDto.toResponseDto(goods);

        return new ResponseDto("SUCCESS", responseDto);
    }

    private Goods checkValidGoods(Long id) {
        return goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다"));
    }

    private void saveFileAndUrl(MultipartFile file, Goods goods) throws Exception {
        String path = "/goods_" + goods.getId() + "/image";
        String uploadedFilePath = storageService.store(path, file);

        goods.updateUrl(uploadedFilePath);
    }
}
