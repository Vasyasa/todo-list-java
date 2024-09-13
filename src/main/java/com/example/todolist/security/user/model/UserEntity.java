package com.example.todolist.security.user.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity {

        @javax.persistence.Id
        @Id
        private String email;

        private String username;
        private String password;
}
