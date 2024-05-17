package com.bookingsiemens.BookingSiemens.CustomExceptions;

public class DuplicatedInstanceException extends RuntimeException {

    public DuplicatedInstanceException(String message) {
        super(message);
    }
}