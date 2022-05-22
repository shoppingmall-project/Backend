package shoppingmall.core.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccount(String account);

    @Query(value = "select * from member where id = ?", nativeQuery = true)
    Member findMemberById(Long id);

    @Query(value = "select * from member", nativeQuery = true)
    List<Member> findMemberList();

    @Transactional
    void deleteByAccount(String account);

}
