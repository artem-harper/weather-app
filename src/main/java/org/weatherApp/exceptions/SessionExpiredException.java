package org.weatherApp.exceptions;

public class SessionExpiredException extends RuntimeException{
    public SessionExpiredException(){
        super("Session expired");
    }
}
