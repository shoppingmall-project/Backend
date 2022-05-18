package shoppingmall.core.domain.Goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query(value = "Select * from goods", nativeQuery = true)
    List<Goods> findAllGoods();
}
