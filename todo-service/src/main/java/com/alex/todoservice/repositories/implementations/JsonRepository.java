package com.alex.todoservice.repositories.implementations;

import com.alex.todoservice.exceptions.EntryNotFoundException;
import com.alex.todoservice.models.Todo;
import com.alex.todoservice.repositories.ObjectRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("JsonRepository")
public class JsonRepository implements ObjectRepository<Todo> {

    final Gson gson;

    private static final String jsonFilePath = "todos.json";
    private static final Path jsonPath = Paths.get(jsonFilePath);
    private static final File jsonFile = new File(jsonFilePath);
    private static final Type objectType = new TypeToken<List<Todo>>() {}.getType();

    private List<Todo> todos;

    public JsonRepository(Gson gson) {
        try {

            this.gson = gson;

            if (jsonFile.exists()) {
                todos = loadTodos();
            } else {

                jsonFile.createNewFile();
                todos = new ArrayList<>();
                save();

            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public Todo get(long id) throws EntryNotFoundException {
        var filteredTodo = todos.stream().filter(t -> t.getId() == id).findFirst();

        if (filteredTodo.isPresent())
            return filteredTodo.get();

        throw new EntryNotFoundException(id);
    }

    @Override
    public List<Todo> getAll() {
        return todos;
    }

    @Override
    public void add(Todo todo) {
        long id = getAll().size() + 1;
        todo.setId(id);

        todos.add(todo);
        save();
    }

    @Override
    public void update(long id, Todo todo) throws EntryNotFoundException {
        var todoToUpdate = todos.stream().filter(t -> t.getId() == id).findFirst();

        if (!todoToUpdate.isPresent())
            throw new EntryNotFoundException(id);

        todo.setId(id);
        todos.set(todos.indexOf(todoToUpdate.get()), todo);
        save();
    }

    @Override
    public void delete(long id) {
        var todoToRemove = todos.stream().filter(t -> t.getId() == id).findFirst().orElse(null);

        if (todoToRemove == null)
            return;

        todos.remove(todoToRemove);
        save();
    }

    private void save() {
        try (var bufferedWriter = Files.newBufferedWriter(jsonPath)) {

            var json = gson.toJson(todos);
            bufferedWriter.write(json);

        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private List<Todo> loadTodos() {
        try {
            var jsonContent = new FileReader(jsonFilePath);
            List<Todo> list = gson.fromJson(jsonContent, objectType);
            return list;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
