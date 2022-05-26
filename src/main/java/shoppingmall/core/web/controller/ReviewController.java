package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.service.review.ReviewService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.review.ReviewCreateRequestDto;
import shoppingmall.core.web.dto.review.ReviewUpdateRequestDto;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/goods/{goodsId}/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    //댓글 생성
    @PostMapping()
    public ResponseDto createReview(@PathVariable Long goodsId, @ModelAttribute ReviewCreateRequestDto requestDto,
                                     @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
        return reviewService.createReview(goodsId, requestDto, file);
    }

    //댓글 리스트 조회
    @GetMapping()
    public ResponseDto findReviewList(@PathVariable Long goodsId) {
        return reviewService.findReviewList(goodsId);
    }

    //댓글 조회
    @GetMapping("/{id}")
    public ResponseDto findReviewById(@PathVariable Long goodsId, @PathVariable Long id) {
        return reviewService.findReviewById(goodsId, id);
    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseDto deleteReview(@PathVariable Long goodsId, @PathVariable Long id) throws Exception {
        return reviewService.deleteReview(goodsId, id);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseDto updateReview(@PathVariable Long goodsId, @PathVariable Long id,
                                     @Valid @ModelAttribute ReviewUpdateRequestDto requestDto,
                                     @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return reviewService.updateReview(goodsId, id, requestDto, file);
    }
}