package com.todo.app.service;

import com.todo.app.model.request.AddTodoRequest;
import com.todo.app.model.response.AppResponse;
import com.todo.app.model.request.EditTodoRequest;
import com.todo.app.model.response.CustomPage;
import com.todo.app.model.response.TodoList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TodoService {
    AppResponse addTodo(AddTodoRequest addTodoRequest);
    AppResponse markComplete(UUID todoId);
    AppResponse deleteTodo(UUID todoId);
    AppResponse editTodo(UUID todoId, EditTodoRequest editTodoRequest);
    CustomPage<TodoList> getTodos(Pageable pageable);
}
