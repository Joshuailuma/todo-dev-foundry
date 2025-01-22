package com.todo.app.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisteredUser {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
