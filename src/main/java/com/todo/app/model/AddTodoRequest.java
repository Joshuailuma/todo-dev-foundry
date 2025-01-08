package com.todo.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AddTodoRequest(
        @NotBlank(message = "Title must not be blank")
        @Size(max = 100)
        String title,

        @NotBlank(message = "Title must not be blank")
        @Size(max = 200)
        String text
) {
}
