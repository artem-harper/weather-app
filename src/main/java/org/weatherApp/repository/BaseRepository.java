package org.weatherApp.repository;

import org.weatherApp.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseRepository<K extends Serializable, E extends BaseEntity> implements Repository<K, E>{

    @Override
    public List<E> findAll() {
        return List.of();
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.empty();
    }

    @Override
    public void delete(K id) {

    }
}
