package ru.kaysiodl.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ResultsRepository {
    @PersistenceContext(name = "pg")
    private EntityManager entityManager;

    @Transactional
    public void add(Result result) {
        entityManager.persist(result);
    }

    @Transactional
    public void clear() {
        entityManager.createQuery("DELETE FROM Result").executeUpdate();
    }

    public List<Result> getAll() {
        return entityManager.createQuery("SELECT r FROM Result r ORDER BY r.id", Result.class)
                .getResultList();
    }
}
