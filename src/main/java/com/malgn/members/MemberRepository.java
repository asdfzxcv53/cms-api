package com.malgn.members;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public boolean existsByUsername(String username){
        // 중복 계정 확인

        Long count = em.createQuery(
                "select count(m) from Member m where m.username = :username",
                Long.class
        )
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }

    public Optional<Member> findByUsername(String username){
        List<Member> result = em.createQuery(
                "select m from Member m where m.username = :username",
                Member.class
        )
                .setParameter("username", username)
                .getResultList();

        return result.stream().findFirst();
    }
}
