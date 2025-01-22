package com.todo.app.exception;

import com.todo.app.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

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
        ErrorResponse errorResponse = new ErrorResponse(SOMETHING_WENT_WRONG, "000", BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "004", BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

}
