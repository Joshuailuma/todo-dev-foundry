package com.todo.app.mapper;

import com.todo.app.model.entity.Todo;
import com.todo.app.model.response.TodoList;
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
