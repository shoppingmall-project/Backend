package shoppingmall.core.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findBoardByTitle(String title);

    @Query(value = "SELECT a.* FROM board a LEFT JOIN member b ON a.member_id = b.id WHERE b.name = ?", nativeQuery = true)
    List<Board> findBoardByWriter(String writer);
}
