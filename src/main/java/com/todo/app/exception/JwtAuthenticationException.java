package com.todo.app.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
