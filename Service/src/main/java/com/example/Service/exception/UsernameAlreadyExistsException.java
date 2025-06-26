package com.example.Service.exception;

public class UsernameAlreadyExistsException extends AlreadyExistsException{
    public UsernameAlreadyExistsException (String message){
        super(message);
    }
}