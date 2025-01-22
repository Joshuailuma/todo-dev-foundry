package com.todo.app.model.entity;

import com.todo.app.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String text;
    private boolean completed;
    private Priority priority;
    private LocalDate startDate;
    private LocalDate dueDate;
}