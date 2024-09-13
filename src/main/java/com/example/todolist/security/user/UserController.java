package com.example.todolist.security.user;

import com.example.todolist.security.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserEntity user) {
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully.");
    }

    @DeleteMapping("/remove/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> removeUser(@PathVariable String email) {
        userService.removeUser(email);
        return ResponseEntity.ok("User removed successfully.");
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateUserName(@PathVariable String email, @RequestBody UserEntity user) {
        userService.updateUser(email, user);
        return ResponseEntity.ok("User updated successfully.");
    }

    @GetMapping("/isTokenAlive")
    public ResponseEntity<Boolean> isTokenAlive(@RequestParam String token) {
        boolean isAlive = userService.isTokenAlive(token);
        return ResponseEntity.ok(isAlive);
    }
}
