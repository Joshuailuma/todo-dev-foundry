package com.todo.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError extends RuntimeException{
    private String message;
    private String code;
    private HttpStatus httpStatus;

    public ApiError(String message, String code, HttpStatus httpStatus){
        this.message = message;
        this.code =  code;
        this.httpStatus = httpStatus;
    }
    public ApiError(String message,HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
