package org.weatherApp.exceptions;

public class LocationAlreadyExistException extends RuntimeException{
    public LocationAlreadyExistException(){
        super("Location already added");
    }
}
