package org.weatherApp.exceptions;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(){
        super("User already exists");
    }
}
