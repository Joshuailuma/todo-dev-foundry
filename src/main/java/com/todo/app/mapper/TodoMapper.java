package com.todo.app.mapper;

import com.todo.app.entity.Todo;
import com.todo.app.model.TodoList;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class TodoMapper {

    public TodoList toTodoList(Todo todo){
return TodoList.builder().
        title(todo.getTitle())
        .text(todo.getText())
        .priority(todo.getPriority())
        .completed(todo.isCompleted())
        .startDate(todo.getStartDate())
        .dueDate(todo.getDueDate())
        .build();
    }
}
