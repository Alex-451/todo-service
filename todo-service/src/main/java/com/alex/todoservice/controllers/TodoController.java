package com.alex.todoservice.controllers;

import com.alex.todoservice.repositories.ObjectRepository;
import com.alex.todoservice.models.Todo;
import com.alex.todoservice.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {

    final TodoService todoService;

    public TodoController(@Qualifier("TodoService") TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Object> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping(path = "/{todoId}")
    public ResponseEntity<Object> getTodo(@PathVariable long todoId) {
        return todoService.getTodo(todoId);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @DeleteMapping(path = "/{todoId}")
    public ResponseEntity<Object> deleteTodo(@PathVariable long todoId) {
        return todoService.deleteTodo(todoId);
    }

    @PutMapping(path = "/{todoId}")
    public ResponseEntity<Object> updateTodo(@PathVariable long todoId, @RequestBody Todo updatedTodo) {
        return todoService.updateTodo(todoId, updatedTodo);
    }
}
