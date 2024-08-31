package com.example.todolist.todo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RequestMapping("/api/todos")
@RestController
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<TodoEntity> getAllTodos() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> getTodoById(@PathVariable Long id) {
        Optional<TodoEntity> todoItem = todoService.findById(id);
        return todoItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodoEntity createTodoItem(@RequestBody TodoEntity todoItem) {
        return todoService.save(todoItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoEntity> updateTodoItem(@PathVariable Long id, @RequestBody TodoEntity todoItem) {
        Optional<TodoEntity> existingTodoItem = todoService.findById(id);
        if (existingTodoItem.isPresent()) {
            TodoEntity updatedTodoItem = existingTodoItem.get();
            updatedTodoItem.setTitle(todoItem.getTitle());
            updatedTodoItem.setDescription(todoItem.getDescription());
            updatedTodoItem.setCompleted(todoItem.isCompleted());
            return ResponseEntity.ok(todoService.save(updatedTodoItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        if (todoService.findById(id).isPresent()) {
            todoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
