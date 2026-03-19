package com.malgn.content;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ContentRepository {

    @PersistenceContext
    private EntityManager em;

    public Content save(final Content content) {
        em.persist(content);

        return content;
    }


}
