package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.config.JwtTokenProvider;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.service.GoodsService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //상품 등록
    @PostMapping("/register")
    public ResponseDto createGoods(@RequestBody GoodsCreateRequestDto requestDto) {
        return goodsService.createGoods(requestDto);
    }

    //상품 id로 조회
    @GetMapping("/goods/{id}")
    public ResponseDto findGoods(@PathVariable Long id) {
        return goodsService.findGoods(id);
    }

    //상품 전체 조회
    @GetMapping("/goodslist")
    public List<Goods> findAllGoods() {
        return goodsRepository.findAllGoods();
    }
}
