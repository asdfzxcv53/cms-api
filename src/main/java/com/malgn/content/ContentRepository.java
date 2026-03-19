package com.malgn.content;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<Content> findAll(int offset, int limit, String sortBy, String direction) {
        // sql injection 방지
        String orderBy = getOrderBy(sortBy);
        String dir = getDirection(direction);

        String jpql = "select c from Content c order by " + orderBy + " " + dir;

        return em.createQuery(jpql, Content.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long count() {
        return em.createQuery(
                    "select count(c) from Content c",
                        Long.class
                )
                .getSingleResult();
    }

    public void delete(Content content){
        em.remove(content);
    }

    private String getOrderBy(String sortBy) {
        return switch (sortBy) {
            case "viewCount" -> "c.viewCount";
            case "createdDate" -> "c.createdDate";
            case "id" -> "c.id";
            default -> "c.updatedDate";
        };
    }

    private String getDirection(String direction){
        return direction.equalsIgnoreCase("asc") ? "asc" : "desc";
    }
}
