package shoppingmall.core.service.basket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.domain.basket.Basket;
import shoppingmall.core.domain.basket.BasketRepository;
import shoppingmall.core.web.dto.ResponseDto;
import shoppingmall.core.web.dto.basket.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final BasketRepository basketRepository;

    @Transactional
    @Override
    public ResponseDto createBasket(BasketCreateRequestDto requestDto, Long memberId) {
        checkValidGoods(requestDto.getGoods_id());
        Member member = checkValidMember(memberId);
        Goods goods = checkValidGoods(requestDto.getGoods_id());

        Basket basket = requestDto.toEntity();
        basket.setGoods(goods);
        basket.setMember(member);
        basketRepository.save(basket);

        BasketCreateResponseDto responseDto = new BasketCreateResponseDto(basket.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Transactional
    @Override
    public ResponseDto deleteBasket(Long basketId) {
        checkValidBasket(basketId);

        basketRepository.deleteById(basketId);
        return new ResponseDto("SUCCESS");
    }

    @Transactional
    @Override
    public ResponseDto updateBasket(Long basketId, BasketUpdateReqeustDto requestDto) {
        Basket basket = checkValidBasket(basketId);

        basket.update(requestDto.getCount());

        BasketUpdateResponseDto responseDto = new BasketUpdateResponseDto(basket.getId());

        return new ResponseDto("SUCCESS", responseDto);
    }

    @Override
    public ResponseDto findBasketList(Long memberId) {
        checkValidMember(memberId);

        List<Basket> basketList = basketRepository.findAllByMemberId(memberId);

        List<BasketFindResponseDto> responseDtoList = new ArrayList<>();
        for (Basket basket : basketList) {
            BasketFindResponseDto responseDto = BasketFindResponseDto.toResponseDto(basket);
            responseDtoList.add(responseDto);
        }
        return new ResponseDto("SUCCESS", responseDtoList);
    }

    @Override
    public ResponseDto findBasketById(Long basketId) {
        Basket basket = checkValidBasket(basketId);

        BasketFindResponseDto responseDto = BasketFindResponseDto.toResponseDto(basket);

        return new ResponseDto("SUCCESS", responseDto);

    }

    private Basket checkValidBasket(Long basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new IllegalArgumentException("?????? ??????????????? ????????????"));
    }

    private Member checkValidMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("?????? ????????? ????????????"));
    }

    private Goods checkValidGoods(Long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(() -> new IllegalArgumentException("?????? ????????? ????????????"));
    }

}
