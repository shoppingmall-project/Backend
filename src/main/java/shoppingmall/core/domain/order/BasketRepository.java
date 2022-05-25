package shoppingmall.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByMemberIdAndGoodsId(Long memberId, Long goodsId);
}
