package com.todo.app.service.impl;

import com.todo.app.mapper.TodoMapper;
import com.todo.app.entity.Todo;
import com.todo.app.model.AddTodoRequest;
import com.todo.app.model.AppResponse;
import com.todo.app.model.EditTodoRequest;
import com.todo.app.model.TodoList;
import com.todo.app.repository.TodoRepository;
import com.todo.app.service.TodoService;
import com.todo.app.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    TodoServiceImpl(TodoRepository todoRepository, TodoMapper todoMapper)
    {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    @Override
    public AppResponse addTodo(AddTodoRequest addTodoRequest) {

        Todo todo = Todo.builder()
                .title(addTodoRequest.title())
                .text(addTodoRequest.text())
                .build();
        try {
        todo = todoRepository.save(todo);

        } catch (Exception e) {
            throw new RuntimeException(Constants.COULD_NOT_ADD_TODO);
        }
        return new AppResponse(Constants.TODO_SUCCESSFULLY_SAVED, todo);
    }

    @Override
    public AppResponse deleteTodo(UUID todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->
                new RuntimeException(Constants.TODO_NOT_FOUND));
        try {
                todoRepository.delete(todo);

        } catch (Exception e) {
            throw new RuntimeException(Constants.SOMETHING_WENT_WRONG);
        }

        return new AppResponse(Constants.TODO_DELETED, todo);
    }

    @Override
    public AppResponse editTodo(UUID todoId, EditTodoRequest editTodoRequest) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->
                new RuntimeException(Constants.TODO_NOT_FOUND));

        if (editTodoRequest.title() != null){
            todo.setTitle(editTodoRequest.title());
        }
        if (editTodoRequest.text() != null){
            todo.setText(editTodoRequest.text());
        }

        try {
                todoRepository.save(todo);

        } catch (Exception e) {
            throw new RuntimeException(Constants.SOMETHING_WENT_WRONG);
        }
        return new AppResponse(Constants.TODO_EDITED, todo);
    }

    @Override
    public Page<TodoList> getTodos(Pageable pageable) {
        List<TodoList> todoList = new ArrayList<>();
        Page<Todo> todoPage = todoRepository.findAll(pageable);
        todoList = todoPage.stream().map(todoMapper::toTodoList).toList();
        return new PageImpl<>(todoList, pageable, todoPage.getTotalElements());
    }


}
