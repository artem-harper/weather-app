package org.weatherApp.exceptions;

public class LocationAlreadyAddedException extends RuntimeException{
    public LocationAlreadyAddedException(){
        super("Location already added");
    }
}
