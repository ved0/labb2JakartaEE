package com.example.demo1.service.exceptions;

import jakarta.ws.rs.WebApplicationException;

public class InvalidProductFormatException extends WebApplicationException {
    public InvalidProductFormatException(){
        super();
    }
    public InvalidProductFormatException(String exception){
        super(exception);
    }
}