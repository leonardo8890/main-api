package com.leonardo.mainapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownIdOcorrencia extends RuntimeException {
    public UnknownIdOcorrencia(String message) {
        super(message);
    }
}
