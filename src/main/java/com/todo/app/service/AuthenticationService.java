package com.todo.app.service;

import com.todo.app.model.AppResponse;
import com.todo.app.model.RegistrationRequest;

public interface AuthenticationService {
    AppResponse register(RegistrationRequest registrationRequest);
    AppResponse login(LoginRequest loginRequest);
}
