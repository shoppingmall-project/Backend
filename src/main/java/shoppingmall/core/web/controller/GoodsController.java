package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.service.goods.GoodsService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    //상품 등록
    @PostMapping()
    public ResponseDto createGoods(@Valid @ModelAttribute GoodsCreateRequestDto requestDto, @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) throws Exception {
        Long memberId = createSession(session);
        return goodsService.createGoods(requestDto, file, memberId);
    }

    //상품 id로 조회
    @GetMapping("/{goodsId}")
    public ResponseDto findGoodsById(@PathVariable Long goodsId) {
        return goodsService.findGoodsById(goodsId);
    }

    //상품 전체 조회
    @GetMapping()
    public ResponseDto findGoodsList() {
        return goodsService.findGoodsList();
    }

    //상품 삭제
    @DeleteMapping("/{goodsId}")
    public ResponseDto deleteGoods(@PathVariable Long goodsId, HttpSession session) throws Exception {
        Long memberId = createSession(session);
        return goodsService.deleteGoods(goodsId, memberId);
    }

    //상품 수정
    @PutMapping("/{goodsId}")
    public ResponseDto updateGoods(@PathVariable Long goodsId, @Valid @ModelAttribute GoodsUpdateRequestDto requestDto, @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) throws Exception {
        Long memberId = createSession(session);
        return goodsService.updateGoods(goodsId, requestDto, file, memberId);
    }

    private Long createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        return (Long) session.getAttribute("memberId");
    }
}
