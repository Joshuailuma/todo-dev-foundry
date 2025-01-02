package com.todo.app.controller;

import com.todo.app.model.AppResponse;
import com.todo.app.model.RegistrationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @PostMapping("/register")
    AppResponse register(RegistrationRequest registrationRequest){

    }


}
