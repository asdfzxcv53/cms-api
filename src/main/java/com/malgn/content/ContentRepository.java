package com.malgn.content;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContentRepository {

    @PersistenceContext
    private EntityManager em;

    public Content save(final Content content) {
        em.persist(content);

        return content;
    }

    public Optional<Content> findById(Long id) {
        return Optional.ofNullable(em.find(Content.class, id));
    }
}
