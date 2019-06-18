package com.akatkar.samples.influxdb.database.util;

public class InvalidAnnotationException extends Exception{

    public InvalidAnnotationException(String message) {
        super(message);
    }

    public InvalidAnnotationException(String message, Throwable cause) {
        super(message, cause);
    } 
}
