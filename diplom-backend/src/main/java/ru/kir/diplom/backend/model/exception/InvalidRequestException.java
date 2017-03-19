package ru.kir.diplom.backend.model.exception;

import org.springframework.validation.Errors;

/**
 * Created by Kirill Zhitelev on 19.03.2017.
 */
public class InvalidRequestException extends RuntimeException {
    private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
