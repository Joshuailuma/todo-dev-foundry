package com.todo.app.exception;

import com.todo.app.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.todo.app.utils.Constants.SOMETHING_WENT_WRONG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadableException(HttpMessageNotReadableException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "001", BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerException(HttpMessageNotReadableException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "003", NOT_FOUND);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ErrorResponse> handleApiError(ApiError e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "002", e.getHttpStatus());
        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "000", BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "004", BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(this::buildErrorMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("Validation failed", "006", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String buildErrorMessage(FieldError fieldError) {
        return String.format("Field '%s' %s", fieldError.getField(), fieldError.getDefaultMessage());
    }
}
