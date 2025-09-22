package org.weatherApp.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import org.weatherApp.entity.Session;

import java.util.UUID;

@Repository
public class SessionRepository extends BaseRepository<UUID, Session> {

    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Session.class);
    }
}
