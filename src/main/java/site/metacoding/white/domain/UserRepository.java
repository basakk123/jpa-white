package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // IoC 등록
public class UserRepository {

    // DI
    private final EntityManager em;

    public User save(User user) {
        // Persistence Context 에 영속화 시키기 => 자동 flush (트랜잭션 종료시)
        System.out.println("ccc : " + user.getId()); // 영속화 되기 전
        em.persist(user);
        System.out.println("ccc : " + user.getId()); // 영속화 된 후 ( DB와 동기화 된다 )
        return user;
    }

    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

}
