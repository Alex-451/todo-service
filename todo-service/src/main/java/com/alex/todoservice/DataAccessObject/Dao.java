package com.alex.todoservice.DataAccessObject;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface Dao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void add(T t);

    void update(T t);

    void delete(long id);

    void delete(T t);
}
