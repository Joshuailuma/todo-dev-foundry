package com.todo.app.controller;

import com.todo.app.model.*;
import com.todo.app.service.TodoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Tag(name = "Todo", description = "This handles todo services")
@OpenAPIDefinition(
        info = @Info(title = "Todo Controller", version = "1.0", description = "Todo controller documentation"))

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    TodoController(TodoService todoService){
        this.todoService =todoService;
    }


    @Operation(summary = "Add todo", description = "Add a todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No Record Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!")
    })
    @PostMapping("/add-todo")
    ResponseEntity<AppResponse> addTodo(@Valid @RequestBody AddTodoRequest addTodoRequest){
       AppResponse response = todoService.addTodo(addTodoRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Add todo", description = "Add a todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No Record Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!")
    })
    @PostMapping("/mark-complete/{todoId}")
    ResponseEntity<AppResponse> addTodo(@PathVariable UUID todoId){
        AppResponse response = todoService.markComplete(todoId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Edit todo", description = "Edit a todo using the todo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No Record Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!")
    })
    @PatchMapping("/edit-todo/{todoId}")
    ResponseEntity<AppResponse> editTodo(@PathVariable UUID todoId,
                                         @RequestBody EditTodoRequest editTodoRequest){
        AppResponse response = todoService.editTodo(todoId, editTodoRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete todo", description = "Deletes a todo using its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No Record Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!")
    })
    @DeleteMapping("/delete-todo/{todoId}")
    ResponseEntity<AppResponse> deleteTodo(@PathVariable UUID todoId){
        AppResponse response = todoService.deleteTodo(todoId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get todos", description = "Get all todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = AppResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No Record Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error!")
    })
    @GetMapping("/get-todos")
    ResponseEntity<Page<TodoList>> getTodos(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "50") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoList> response = todoService.getTodos(pageable);
        return ResponseEntity.ok(response);
    }
}
