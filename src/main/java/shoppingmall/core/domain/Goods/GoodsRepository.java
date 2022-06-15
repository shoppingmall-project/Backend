package shoppingmall.core.domain.Goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query(value = "SELECT * FROM goods WHERE id = ?", nativeQuery = true)
    Goods findByGoodsId(Long goodsId);

    @Query(value = "SELECT DISTINCT category FROM goods", nativeQuery = true)
    List<String> findGoodsCategory();

    @Query(value = "SELECT DISTINCT brand FROM goods", nativeQuery = true)
    List<String> findGoodsBrand();

    @Query(value = "SELECT DISTINCT country FROM goods", nativeQuery = true)
    List<String> findGoodsCountry();

    @Query(value = "SELECT * FROM goods WHERE category = ?", nativeQuery = true)
    List<Goods> findByCategory(String category);
}
