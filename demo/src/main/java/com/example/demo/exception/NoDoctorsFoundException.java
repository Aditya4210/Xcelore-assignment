package com.example.demo.exception;


public class NoDoctorsFoundException extends RuntimeException {
    public NoDoctorsFoundException(String message) {
        super(message);
    }
}
