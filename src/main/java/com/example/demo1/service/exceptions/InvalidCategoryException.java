package com.example.demo1.service.exceptions;

import jakarta.ws.rs.WebApplicationException;

public class InvalidCategoryException extends WebApplicationException {
    public InvalidCategoryException(){
        super();
    }
    public InvalidCategoryException(String exception){
        super(exception);
    }
}
