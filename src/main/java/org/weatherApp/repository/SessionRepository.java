package org.weatherApp.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.entity.Session;

import java.util.UUID;

@Repository
@Transactional
public class SessionRepository extends BaseRepository<UUID, Session> {

    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Session.class);
    }
}
