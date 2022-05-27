package shoppingmall.core.domain.Goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query(value = "SELECT * FROM goods WHERE id = ?", nativeQuery = true)
    Goods findByGoodsId(Long goodsId);
}
