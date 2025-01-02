package com.todo.app.model;

import com.todo.app.enums.Role;
import lombok.Builder;

@Builder
public record RegistrationRequest(
        String firstName,
        String lastName,
        String username,
        String password,
        Role role
) {
}
