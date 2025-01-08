package com.todo.app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AuthenticationResponse {
    private String token;
    private Long expiresAt;
}
