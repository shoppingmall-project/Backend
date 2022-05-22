package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.service.goods.GoodsService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    //상품 등록
    @PostMapping()
    public ResponseDto createGoods(@RequestBody GoodsCreateRequestDto requestDto) {
        return goodsService.createGoods(requestDto);
    }

    //상품 id로 조회
    @GetMapping("/{id}")
    public ResponseDto findGoodsById(@PathVariable Long id) {
        return goodsService.findGoodsById(id);
    }

    //상품 전체 조회
    @GetMapping()
    public ResponseDto findGoodsList() {
        return goodsService.findGoodsList();
    }

    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseDto deleteGoods(@PathVariable Long id) {
        return goodsService.deleteGoods(id);
    }

    //상품 수정
    @PutMapping("/{id}")
    public ResponseDto updateGoods(@PathVariable Long id, @Valid @RequestBody GoodsUpdateRequestDto requestDto) {
        return goodsService.updateGoods(id, requestDto);
    }
}
