package ru.kir.diplom.backend.model.exception;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kir.diplom.backend.model.rest.HttpResponseDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kirill Zhitelev on 19.03.2017.
 */
@ControllerAdvice
public class SourceExceptionHandler extends ResponseEntityExceptionHandler {
    private ModelMapper mapper = new ModelMapper();

    @ExceptionHandler({InvalidRequestException.class})
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        InvalidRequestException exception = (InvalidRequestException) e;
        List<FieldErrorResource> fieldErrors = new ArrayList<>();

        fieldErrors.addAll(exception.getErrors().getFieldErrors()
                .stream()
                .map(fieldError -> mapper.map(fieldError, FieldErrorResource.class))
                .collect(Collectors.toList()));

        HttpResponseDescriptor responseDescriptor = new HttpResponseDescriptor();
        responseDescriptor.setCode(HttpStatus.BAD_REQUEST.value());
        responseDescriptor.setFieldErrors(fieldErrors);
        responseDescriptor.setMessage("Wrong field(s)");

        return handleExceptionInternal(e, responseDescriptor, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NullRequestException.class})
    protected ResponseEntity<Object> handleNullRequest(RuntimeException e, WebRequest request) {
        NullRequestException exception = (NullRequestException) e;
        String message = "No object of class "
                + exception.getClazz().getName() + " with " +  exception.getNameField() + " - " + exception.getAttribute();

        HttpResponseDescriptor responseDescriptor = new HttpResponseDescriptor();
        responseDescriptor.setCode(HttpStatus.BAD_REQUEST.value());
        responseDescriptor.setMessage(message);

        return handleExceptionInternal(exception, responseDescriptor, null, HttpStatus.BAD_REQUEST, request);
    }

}
