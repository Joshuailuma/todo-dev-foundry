package com.todo.app.controller;

import com.todo.app.model.AddTodoRequest;
import com.todo.app.model.AppResponse;
import com.todo.app.model.EditTodoRequest;
import com.todo.app.model.TodoList;
import com.todo.app.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    TodoController(TodoService todoService){
        this.todoService =todoService;
    }

    @PostMapping("/add-todo")
    ResponseEntity<AppResponse> addTodo(@Valid @RequestBody AddTodoRequest addTodoRequest){
       AppResponse response = todoService.addTodo(addTodoRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/edit-todo/{todoId}")
    ResponseEntity<AppResponse> editTodo(@PathVariable UUID todoId,
                                         @RequestBody EditTodoRequest editTodoRequest){
        AppResponse response = todoService.editTodo(todoId, editTodoRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-todo/{todoId}")
    ResponseEntity<AppResponse> deleteTodo(@PathVariable UUID todoId){
        AppResponse response = todoService.deleteTodo(todoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-todos")
    ResponseEntity<Page<TodoList>> getTodos(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "50") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoList> response = todoService.getTodos(pageable);
        return ResponseEntity.ok(response);
    }
}
