package com.szatkowskiartur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughCreditException extends Exception{

    public NotEnoughCreditException(String message) {
        super(message);
    }

}
