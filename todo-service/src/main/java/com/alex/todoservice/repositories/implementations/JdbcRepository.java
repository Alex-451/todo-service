package com.alex.todoservice.repositories.implementations;

import com.alex.todoservice.exceptions.EntryNotFoundException;
import com.alex.todoservice.models.Todo;
import com.alex.todoservice.repositories.ObjectRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("JdbcRepository")
public class JdbcRepository implements ObjectRepository<Todo> {

    final JdbcTemplate jdbcTemplate;

    public JdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Todo> rowMapper = (rs, rowNum) -> {
        Todo todo = new Todo(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("creation_date"),
                rs.getDate("due_date"),
                rs.getBoolean("is_completed"));
        return todo;
    };

    @Override
    public Todo get(long id) throws EntryNotFoundException {
        String sql = "SELECT * FROM todos WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            throw new EntryNotFoundException(id);
        }
    }

    @Override
    public List<Todo> getAll() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void add(Todo todo) {
        String sql = "INSERT INTO todos(title, description, creation_date, due_date, is_completed) VALUES(?,?,?,?,?)";
        var insert = jdbcTemplate.update(sql, todo.getTitle(), todo.getDescription(), todo.getCreationDate(), todo.getDueDate(), false);
    }

    @Override
    public void update(long id, Todo todo) throws EntryNotFoundException {
        String sql = "UPDATE todos SET title = ?, description = ?, creation_date = ?, due_date = ?, is_completed = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getDescription(), todo.getCreationDate(), todo.getDueDate(), todo.isCompleted(), id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM todos WHERE id = ?", id);
    }
}
