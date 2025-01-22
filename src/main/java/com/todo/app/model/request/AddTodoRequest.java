package com.todo.app.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todo.app.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AddTodoRequest(
        @NotBlank(message = "must not be blank")
        @Size(max = 100)
        String title,

        @NotBlank(message = "text must not be blank")
        @Size(max = 200)
        String text,

        @NotNull(message = "must be LOW, MEDIUM or HIGH")
        Priority priority,

        @NotNull(message = "must be in format dd-MM-yyyy")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dueDate
) {
}
