package org.weatherApp.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(){
        super("Invalid password");
    }
}
