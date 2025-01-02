package com.todo.app.service;

import com.todo.app.entity.Todo;
import com.todo.app.model.AddTodoRequest;
import com.todo.app.model.AppResponse;
import com.todo.app.model.EditTodoRequest;
import com.todo.app.model.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TodoService {
    AppResponse addTodo(AddTodoRequest addTodoRequest);
    AppResponse deleteTodo(UUID todoId);
    AppResponse editTodo(UUID todoId, EditTodoRequest editTodoRequest);
    Page<TodoList> getTodos(Pageable pageable);
}
