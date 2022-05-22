package shoppingmall.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "select * from board where id = ?", nativeQuery = true)
    Board findBoardById(Long id);

    @Query(value = "Select * from board", nativeQuery = true)
    List<Board> findBoardList();
}
