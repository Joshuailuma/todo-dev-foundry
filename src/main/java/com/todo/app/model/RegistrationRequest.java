package com.todo.app.model;

import com.todo.app.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationRequest{
    @NotBlank(message = "First name required")
       private String firstName;
    @NotBlank(message = "Last name required")
       private String lastName;
    @NotBlank(message = "Email required")
      private String email;
    @NotBlank(message = "Password required")
       private String password;

}
