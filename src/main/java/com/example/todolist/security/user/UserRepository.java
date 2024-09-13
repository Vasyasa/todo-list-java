package com.example.todolist.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.todolist.security.user.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

}