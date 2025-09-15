package org.weatherApp.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.entity.User;

import java.util.Optional;

@Transactional
@Repository
public class UserRepository extends BaseRepository<Integer, User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public Optional<User> findByLogin(User user){
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from User u where u.login=:userlogin", User.class)
                .setParameter("userlogin", user.getLogin()).getResultList().stream().findFirst();
    }
}
