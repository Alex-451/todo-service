package com.alex.todoservice.exceptions;

public class EntryNotFoundException extends Exception{

    public EntryNotFoundException(long id) {
        super("Entry with the id " +id+ " not found");
    }
}
