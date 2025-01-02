package com.todo.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EditTodoRequest(
        @Size(max = 100)
        String title,

        @Size(max = 200)
        String text
) {
}
