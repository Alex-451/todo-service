package com.alex.todoservice.services;

import com.alex.todoservice.exceptions.EntryNotFoundException;
import com.alex.todoservice.models.Todo;
import com.alex.todoservice.repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service("TodoService")
public class TodoService {

    final ObjectRepository repository;

    public TodoService(@Qualifier("JsonRepository") ObjectRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Object> getTodos() {
        return ResponseEntity.ok(repository.getAll());
    }

    public ResponseEntity<Object> getTodo(long todoId) {
        try {
            return ResponseEntity.ok(repository.get(todoId));
        } catch (EntryNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> addTodo(Todo todo) {
        repository.add(todo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        todo.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    public ResponseEntity<Object> updateTodo(long todoId, Todo updatedTodo) {
        try {
            repository.update(todoId, updatedTodo);
            return ResponseEntity.ok().build();
        } catch (EntryNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> deleteTodo(long todoId) {
        repository.delete(todoId);
        return ResponseEntity.ok().build();
    }
}
