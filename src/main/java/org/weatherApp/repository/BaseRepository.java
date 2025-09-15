package org.weatherApp.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.entity.BaseEntity;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public abstract class BaseRepository<K extends Serializable, E extends BaseEntity>{

    final SessionFactory sessionFactory;

    private final Class<E> clazz;


    public BaseRepository(SessionFactory sessionFactory, @Autowired(required = false) Class<E> clazz) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
    }

    public E save(E entity){
        Session session = sessionFactory.getCurrentSession();

        session.persist(entity);

        return entity;
    }

    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = builder.createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));

        return session.createQuery(criteriaQuery).getResultList();
    }

    public Optional<E> findById(K id) {
        Session session = sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.find(clazz, id));
    }

    public void delete(K id) {
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.find(clazz, id));
    }
}
