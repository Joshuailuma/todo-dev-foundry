package com.todo.app.model;

public record AppResponse(
        String message,
        Object result
) {
}
