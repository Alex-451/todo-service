package com.alex.todoservice.models;

import java.util.Date;

public class Todo {
    public Todo(long id, String title, String description, Date creationDate, Date dueDate, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;

        this.isCompleted = isCompleted;
    }

    private long id;
    private String title;
    private String description;
    private Date creationDate;
    private Date dueDate;
    private boolean isCompleted;


    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                ", isCompleted=" + isCompleted +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
