package com.todo.app.service.impl;

import com.todo.app.exception.ApiError;
import com.todo.app.mapper.TodoMapper;
import com.todo.app.model.entity.Todo;
import com.todo.app.model.request.AddTodoRequest;
import com.todo.app.model.response.AppResponse;
import com.todo.app.model.request.EditTodoRequest;
import com.todo.app.model.response.CustomPage;
import com.todo.app.model.response.TodoList;
import com.todo.app.repository.TodoRepository;
import com.todo.app.service.TodoService;
import com.todo.app.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.todo.app.utils.Constants.FUTURE_DUE_DATE;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String startDate = LocalDate.now().format(formatter);
        LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);

        Todo todo = Todo.builder()
                .title(addTodoRequest.title())
                .text(addTodoRequest.text())
                .priority(addTodoRequest.priority())
                .startDate(parsedStartDate)
                .dueDate(addTodoRequest.dueDate())
                .build();

        if (addTodoRequest.dueDate().isBefore(LocalDate.now())){
            throw new ApiError(FUTURE_DUE_DATE, HttpStatus.BAD_REQUEST);
        }
        try {
        todo = todoRepository.save(todo);

        } catch (Exception e) {
            throw new RuntimeException(Constants.COULD_NOT_ADD_TODO);
        }
        return new AppResponse(Constants.TODO_SUCCESSFULLY_SAVED, todo);
    }

    @Override
    public AppResponse markComplete(UUID todoId) {

        Todo todo = todoRepository.findById(todoId).orElseThrow(()->
                new RuntimeException(Constants.TODO_NOT_FOUND));

        todo.setCompleted(true);
        try {
            todoRepository.save(todo);

        } catch (Exception e) {
            throw new RuntimeException(Constants.SOMETHING_WENT_WRONG);
        }
        return new AppResponse(Constants.TODO_COMPLETED, todo);
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
        if (editTodoRequest.completed()){
            todo.setCompleted(true);
        }
        if (editTodoRequest.dueDate() != null){
            if (editTodoRequest.dueDate().isBefore(LocalDate.now())){
                throw new ApiError(FUTURE_DUE_DATE, HttpStatus.BAD_REQUEST);
            }
            todo.setDueDate(editTodoRequest.dueDate());
        }

        try {
                todoRepository.save(todo);
        } catch (Exception e) {
            throw new RuntimeException(Constants.SOMETHING_WENT_WRONG);
        }
        return new AppResponse(Constants.TODO_EDITED, todo);
    }

    @Override
    public CustomPage<TodoList> getTodos(Pageable pageable) {
        int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        Pageable updatedPageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());

        List<TodoList> todoList = new ArrayList<>();
        Page<Todo> todoPage = todoRepository.findAll(updatedPageable);
        todoList = todoPage.stream().map(todoMapper::toTodoList).toList();
        return new CustomPage<>( new PageImpl<>(todoList, pageable, todoPage.getTotalElements()));
    }
}
