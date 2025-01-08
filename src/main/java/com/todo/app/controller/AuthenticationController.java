package com.todo.app.controller;

import com.todo.app.model.AppResponse;
import com.todo.app.model.LoginRequest;
import com.todo.app.model.RegistrationRequest;
import com.todo.app.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    ResponseEntity<AppResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest){
       AppResponse response = authenticationService.register(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<AppResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        AppResponse response = authenticationService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
