package shop.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.shopping.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member , Long> {

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    Member findByUsername(String username);
    Member findByRefreshToken(String refreshToken);

    Member findMemberByNickname(String nickname);

}
