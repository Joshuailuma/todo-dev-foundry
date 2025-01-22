package com.todo.app.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationRequest{
    @NotBlank(message = "required")
       private String firstName;
    @NotBlank(message = "required")
       private String lastName;
    @NotBlank(message = "required")
      private String email;
    @NotBlank(message = "required")
       private String password;

}
