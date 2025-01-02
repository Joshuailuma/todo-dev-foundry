package com.todo.app.model;

import com.todo.app.enums.Role;
import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {
}
