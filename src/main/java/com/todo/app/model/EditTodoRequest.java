package com.todo.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todo.app.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EditTodoRequest(
        @Size(max = 100)
        String title,

        @Size(max = 200)
        String text,

        Priority priority,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dueDate
) {
}
