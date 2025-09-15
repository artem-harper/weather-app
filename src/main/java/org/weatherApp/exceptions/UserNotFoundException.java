package org.weatherApp.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("Invalid username or password");
    }
}
