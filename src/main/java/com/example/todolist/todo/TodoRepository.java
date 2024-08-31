package com.example.todolist.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}


