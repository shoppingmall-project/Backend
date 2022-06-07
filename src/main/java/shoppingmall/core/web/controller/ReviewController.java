package shoppingmall.core.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.service.review.ReviewService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.review.ReviewCreateRequestDto;
import shoppingmall.core.web.dto.review.ReviewUpdateRequestDto;

import javax.servlet.http.HttpSession;
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
                                     @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) throws Exception{
        Long memberId = createSession(session);
        return reviewService.createReview(goodsId, requestDto, file, memberId);
    }

    //댓글 리스트 조회
    @GetMapping()
    public ResponseDto findReviewList(@PathVariable Long goodsId) {
        return reviewService.findReviewList(goodsId);
    }

    //댓글 조회
    @GetMapping("/{reviewId}")
    public ResponseDto findReviewById(@PathVariable Long goodsId, @PathVariable Long reviewId) {
        return reviewService.findReviewById(goodsId, reviewId);
    }

    //댓글 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseDto deleteReview(@PathVariable Long goodsId, @PathVariable Long reviewId, HttpSession session) throws Exception {
        Long memberId = createSession(session);
        return reviewService.deleteReview(goodsId, reviewId, memberId);
    }

    //댓글 수정
    @PutMapping("/{reviewId}")
    public ResponseDto updateReview(@PathVariable Long goodsId, @PathVariable Long reviewId,
                                    @Valid @ModelAttribute ReviewUpdateRequestDto requestDto,
                                    @RequestParam(value = "file", required = false) MultipartFile file,
                                    HttpSession session) throws Exception {
        Long memberId = createSession(session);
        return reviewService.updateReview(goodsId, reviewId, requestDto, file, memberId);
    }

    private Long createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("로그인을 하지 않은 상태입니다.");
        }
        return (Long) session.getAttribute("memberId");
    }
}
