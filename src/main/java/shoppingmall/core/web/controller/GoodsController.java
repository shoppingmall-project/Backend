package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.service.goods.GoodsService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    //상품 등록
    @PostMapping()
    public ResponseDto createGoods(@Valid @ModelAttribute GoodsCreateRequestDto requestDto,
                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                   @AuthenticationPrincipal Member member) throws Exception {
        return goodsService.createGoods(requestDto, file, member.getId());
    }

    //상품 id로 조회
    @GetMapping("/{goodsId}")
    public ResponseDto findGoodsById(@PathVariable Long goodsId) {
        return goodsService.findGoodsById(goodsId);
    }

    //상품 카테고리로 조회
    @GetMapping("/category/{category}")
    public ResponseDto findGoodsByCategory(@PathVariable String category) {
        return goodsService.findGoodsByCategory(category);
    }
    
    //상품 전체 조회
    @GetMapping()
    public ResponseDto findGoodsList() {
        return goodsService.findGoodsList();
    }

    //상품 삭제
    @DeleteMapping("/{goodsId}")
    public ResponseDto deleteGoods(@PathVariable Long goodsId, @AuthenticationPrincipal Member member) throws Exception {
        return goodsService.deleteGoods(goodsId, member.getId());
    }

    //상품 수정
    @PutMapping("/{goodsId}")
    public ResponseDto updateGoods(@PathVariable Long goodsId, @Valid @ModelAttribute GoodsUpdateRequestDto requestDto,
                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                   @AuthenticationPrincipal Member member) throws Exception {
        return goodsService.updateGoods(goodsId, requestDto, file, member.getId());
    }
}
