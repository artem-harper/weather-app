package org.weatherApp.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.weatherApp.entity.Location;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location>{

    public LocationRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Location.class);
    }
}
