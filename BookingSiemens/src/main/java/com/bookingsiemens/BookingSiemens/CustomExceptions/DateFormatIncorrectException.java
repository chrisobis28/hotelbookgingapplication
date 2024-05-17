package com.bookingsiemens.BookingSiemens.CustomExceptions;

public class DateFormatIncorrectException extends RuntimeException {

    public DateFormatIncorrectException(String message) {
        super(message);
    }
}