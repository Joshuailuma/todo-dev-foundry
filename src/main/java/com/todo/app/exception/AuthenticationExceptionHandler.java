package com.todo.app.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
@Slf4j
@Configuration
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Authentication exception type: " + authException.getClass().getName());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());


        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write("{ \"errorMessage\": \"" + authException.getMessage() + "\" }");
        writer.flush();
    }
}
