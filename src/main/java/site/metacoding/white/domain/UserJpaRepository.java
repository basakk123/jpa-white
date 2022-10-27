package site.metacoding.white.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    // @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);
    // findByEmail, findByGender By 뒤에 있는 이름을 자동으로 where로 걸어줌 : jpql 네임드 쿼리
}
