package org.weatherApp.exceptions;

public class SessionNotFoundException extends RuntimeException{
    public SessionNotFoundException(){
        super("Session not found exception");
    }
}
