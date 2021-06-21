package com.alex.todoservice.DataAccessObject.implementations;

import com.alex.todoservice.DataAccessObject.Dao;
import com.alex.todoservice.Model.Todo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("JsonDao")
public class JsonDao implements Dao<Todo> {

    @Autowired
    Gson gson;

    private final String jsonFile = "todos.json";
    private List<Todo> todos = new ArrayList<>();

    public JsonDao() {
        if(Files.notExists(Paths.get(jsonFile))) {
            try {
                Files.createFile(Paths.get(jsonFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Todo> get(long id) {
        try (var reader = new JsonReader(new FileReader(jsonFile))) {
            List<Todo> todos = gson.fromJson(reader, Todo[].class);
            var todo = todos.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
            return Optional.ofNullable(todo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Todo> getAll() {
        try (var reader = new JsonReader(new FileReader(jsonFile))) {
            var userListType = new TypeToken<ArrayList<Todo>>(){}.getType();
            List<Todo> todos = gson.fromJson(reader, userListType);
            return todos == null ? new ArrayList<Todo>() : todos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Todo todo) {
        try {
            todos.add(todo);
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void update(Todo todo) {

    }

    @Override
    public void delete(long id) {
        try {
            var todo = todos.stream().filter(t -> t.getId() == id).findFirst().orElse(null);

            if (todo == null)
                return;

            todos.remove(todos.indexOf(todo));

            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void delete(Todo todo) {
        try {
            todos.remove(todos.indexOf(todo));
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void save() {
        try (var writer = Files.newBufferedWriter(Paths.get(jsonFile), StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            var json = gson.toJson(todos);

            writer.write(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
