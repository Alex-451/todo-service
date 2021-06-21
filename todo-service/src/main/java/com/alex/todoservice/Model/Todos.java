package com.alex.todoservice.Model;

import java.util.ArrayList;
import java.util.List;

public class Todos {
    private List<Todo> todoList;

    public List<Todo> getTodoList() {
        if(todoList == null)
            new ArrayList<>();

        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }
}
