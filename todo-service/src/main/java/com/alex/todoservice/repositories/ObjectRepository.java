package com.alex.todoservice.repositories;

import com.alex.todoservice.exceptions.EntryNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ObjectRepository<T> {

    T get(long id) throws EntryNotFoundException;;

    List<T> getAll();

    void add(T t);

    void update(long id, T t) throws EntryNotFoundException;

    void delete(long id);
}
