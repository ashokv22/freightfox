package com.freightfox.demo.exeptions;

public class TimeSlotConflictException extends RuntimeException {

    public TimeSlotConflictException(String message) {
        super(message);
    }
}
