package com.todo.app.model;

import lombok.Builder;

@Builder
public record AppResponse(
        String message,
        Object result
) {
}
