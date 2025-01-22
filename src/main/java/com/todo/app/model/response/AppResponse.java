package com.todo.app.model.response;

import lombok.Builder;

@Builder
public record AppResponse(
        String message,
        Object result
) {
}
