package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.service.GoodsService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;

    //상품 등록
    @PostMapping("/goods/create")
    public ResponseDto createGoods(@RequestBody GoodsCreateRequestDto requestDto) {
        return goodsService.createGoods(requestDto);
    }

    //상품 id로 조회
    @GetMapping("/goods/{id}")
    public Optional<Goods> findGoodsById(@PathVariable Long id) {
        return goodsRepository.findById(id);
    }

    //상품 전체 조회
    @GetMapping("/goodslist")
    public List<Goods> findAllGoods() {
        return goodsRepository.findAllGoods();
    }

    //상품 삭제
    @DeleteMapping("/goods/{id}")
    public ResponseDto deleteGoods(@PathVariable Long id) {
        return goodsService.deleteGoods(id);
    }

    //상품 수정
    @PutMapping("/goods/{id}")
    public ResponseDto updateGoods(@PathVariable Long id, @Valid @RequestBody GoodsUpdateRequestDto requestDto) {
        return goodsService.updateGoods(id, requestDto);
    }
}
