package com.todo.app.model.response;

import com.todo.app.enums.Priority;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TodoList(
        String title,
        String text,
        Priority priority,
        boolean completed,
        LocalDate startDate,
        LocalDate dueDate

) {
}
