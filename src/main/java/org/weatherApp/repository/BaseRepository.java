package org.weatherApp.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.entity.BaseEntity;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BaseRepository<K extends Serializable, E extends BaseEntity>{

    private final SessionFactory sessionFactory;

    public BaseRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return null;
    }

    public Optional<E> findById(K id) {
        return Optional.empty();
    }

    public void delete(K id) {

    }
}
