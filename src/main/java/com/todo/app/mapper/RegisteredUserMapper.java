package com.todo.app.mapper;

import com.todo.app.model.entity.User;
import com.todo.app.model.response.RegisteredUser;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserMapper {
    public RegisteredUser toRegisteredUser(User user){
      return   RegisteredUser.builder()
              .id(user.getId().toString())
              .email(user.getEmail())
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .build();
    }
}
