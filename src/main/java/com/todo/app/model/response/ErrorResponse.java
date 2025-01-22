package com.todo.app.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String code;
    private HttpStatus httpStatus;
    private List<String> errors;

    public ErrorResponse(String message, String code, List<String> errors){
        this.errors = errors;
        this.code = code;
        this.message = message;

    }

    public ErrorResponse(String message, String code, HttpStatus httpStatus){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;

    }
}
