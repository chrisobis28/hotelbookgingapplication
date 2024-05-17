package com.bookingsiemens.BookingSiemens.CustomExceptions;

public class InstanceNotFoundException extends RuntimeException {

    public InstanceNotFoundException(String message) {
        super(message);
    }
}