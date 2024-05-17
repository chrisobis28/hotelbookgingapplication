package com.bookingsiemens.BookingSiemens.CustomExceptions;

public class FieldNullException extends RuntimeException {

    public FieldNullException(String message) {
        super(message);
    }
}