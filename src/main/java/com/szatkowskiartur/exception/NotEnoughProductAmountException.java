package com.szatkowskiartur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughProductAmountException extends Exception {

    public NotEnoughProductAmountException(String message) {
        super(message);
    }

}
