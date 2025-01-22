package com.todo.app.service;

import com.todo.app.model.response.AppResponse;
import com.todo.app.model.request.LoginRequest;
import com.todo.app.model.request.RegistrationRequest;

public interface AuthenticationService {
    AppResponse register(RegistrationRequest registrationRequest);
    AppResponse login(LoginRequest loginRequest);
}
