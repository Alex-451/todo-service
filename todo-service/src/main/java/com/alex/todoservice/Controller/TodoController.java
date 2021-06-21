package com.alex.todoservice.Controller;

import com.alex.todoservice.DataAccessObject.Dao;
import com.alex.todoservice.Model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {

    @Autowired
    @Qualifier("JsonDao")
    Dao dataAccessObject;

    public TodoController(Dao dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
    }


    @GetMapping(path = "/")
    public List<Todo> getTodos() {
        return dataAccessObject.getAll();
    }

    @GetMapping(path = "/{todoId}")
    public Optional<Todo> getTodo(@PathVariable long todoId) {
        return dataAccessObject.get(todoId);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Object> addTodo(@RequestBody Todo todo) {
        long id = dataAccessObject.getAll().size() + 1;

        todo.setId(id);

        dataAccessObject.add(todo);

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
}
