package com.example.todolist.todo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoEntity> findAll() {
        return todoRepository.findAll();
    }

    public Optional<TodoEntity> findById(Long id) {
        return todoRepository.findById(id);
    }

    public TodoEntity save(TodoEntity todoItem) {
        return todoRepository.save(todoItem);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}
