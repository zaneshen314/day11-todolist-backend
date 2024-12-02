package com.oocl.Exception;

public class TodoNotFoundException extends RuntimeException {

    public static final String ERROR_MSG = "Todo Not Found";

    public TodoNotFoundException() {
        super(ERROR_MSG);
    }

}
