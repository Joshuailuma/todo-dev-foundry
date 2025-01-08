package com.todo.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest{

       @NotBlank(message = "required")
       private String email;

       @NotBlank(message = "required")
       private String password;
}