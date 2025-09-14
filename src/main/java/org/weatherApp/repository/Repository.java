package org.weatherApp.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<K, E> {

    List<E> findAll();

    Optional<E> findById(K id);

    void delete(K id);
}
