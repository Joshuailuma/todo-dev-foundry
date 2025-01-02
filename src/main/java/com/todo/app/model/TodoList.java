package com.todo.app.model;

import lombok.Builder;

@Builder
public record TodoList(
        String title,
        String text
) {
}
