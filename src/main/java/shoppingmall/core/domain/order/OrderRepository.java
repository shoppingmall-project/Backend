package shoppingmall.core.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberIdAndGoodsId(Long memberId, Long goodsId);
}
