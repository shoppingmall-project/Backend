package shoppingmall.core.service.review;

import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.review.ReviewCreateRequestDto;
import shoppingmall.core.web.dto.review.ReviewUpdateRequestDto;

public interface ReviewService {

    ResponseDto createReview(Long goodsId, ReviewCreateRequestDto requestDto, MultipartFile file) throws Exception;

    ResponseDto findReviewList(Long goodsId);

    ResponseDto findReviewById(Long goodsId, Long reviewId);

    ResponseDto deleteReview(Long goodsId, Long reviewId) throws Exception;

    ResponseDto updateReview(Long goodsId, Long reviewId, ReviewUpdateRequestDto requestDto, MultipartFile file) throws Exception;
}
