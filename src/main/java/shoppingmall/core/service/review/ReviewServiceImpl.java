package shoppingmall.core.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.domain.review.Review;
import shoppingmall.core.domain.review.ReviewRepository;
import shoppingmall.core.service.member.MemberService;
import shoppingmall.core.service.storage.StorageService;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.review.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    @Qualifier("FileStorageService")
    private final GoodsRepository goodsRepository;
    private final ReviewRepository reviewRepository;
    private final StorageService storageService;
    private final MemberRepository memberRepository;


    @Transactional
    @Override
    public ResponseDto createReview(Long goodsId, ReviewCreateRequestDto requestDto, MultipartFile file, Long memberId) throws Exception{

        Member member = checkValidMemberId(memberId);
        Goods goods = checkValidGoodsId(goodsId);

        Review review = requestDto.toEntity();
        review.setGoods(goods);
        review.setMember(member);

        Review savedReview = reviewRepository.save(review);

        if(file != null) {
            saveFileAndUrl(goodsId, file, savedReview);
        }
        ReviewCreateResponseDto responseDto = new ReviewCreateResponseDto(savedReview.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto updateReview(Long goodsId, Long reviewId, ReviewUpdateRequestDto requestDto, MultipartFile file, Long memberId) throws Exception {

        Member member = checkValidMemberId(memberId);
        checkValidGoodsId(goodsId);
        Review review = checkValidReview(reviewId);

        if (!Objects.equals(review.getMember().getId(), memberId) && !Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "????????? ????????????..");
        }
        review.updateReview(requestDto.getContent());

        if(review.getImageUrl() != null) {
            if (storageService.delete(review.getImageUrl())) {
                review.updateUrl(null);
            }
        }
        if(file != null) {
            saveFileAndUrl(goodsId, file, review);
        }

        ReviewUpdateResponseDto responseDto = new ReviewUpdateResponseDto(review.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteReview(Long goodsId, Long reviewId, Long memberId) throws Exception{

        Member member = checkValidMemberId(memberId);
        checkValidGoodsId(goodsId);
        Review review = checkValidReview(reviewId);

        if (!Objects.equals(review.getMember().getId(), memberId) && !Objects.equals(member.getRole(), "M")) {
            return new ResponseDto("FAIL", "????????? ????????????..");
        }

        if(review.getImageUrl() != null) {
            if (storageService.delete(review.getImageUrl())) {
                review.updateUrl(null);
            }
        }
        reviewRepository.deleteById(reviewId);

        return new ResponseDto("SUCCESS");
    }

    @Override
    public ResponseDto findReviewList(Long goodsId) {
        checkValidGoodsId(goodsId);
        List<Review> reviewList = reviewRepository.findAllByGoodsId(goodsId);

        List<ReviewFindResponseDto> responseDtoList = new ArrayList<>();
        for(Review review : reviewList) {
            ReviewFindResponseDto responseDto = ReviewFindResponseDto.toResponseDto(review);
            responseDtoList.add(responseDto);
        }

        return new ResponseDto("SUCCESS", responseDtoList);

    }

    @Override
    public ResponseDto findReviewById(Long goodsId, Long reviewId) {
        checkValidGoodsId(goodsId);
        Review review = checkValidReview(reviewId);

        ReviewFindResponseDto responseDto = ReviewFindResponseDto.toResponseDto(review);

        return new ResponseDto("SUCCESS", responseDto);
    }


    private Goods checkValidGoodsId(Long goodsId) {
        return goodsRepository.findById(goodsId)
                .orElseThrow(() -> new IllegalArgumentException("?????? ????????? ????????????."));
    }

    private void saveFileAndUrl(Long goodsId, MultipartFile file, Review review) throws Exception {
        String path = "/goods_" + goodsId + "/review_" + review.getId() + "/image";
        String uploadedFilePath = storageService.store(path, file);

        review.updateUrl(uploadedFilePath);
    }

    private Review checkValidReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("?????? ????????? ????????????."));
    }

    private Member checkValidMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException(" ???????????? ?????? ???????????????. "));
    }

}
