package com.example.todolist.security.oauth;

import com.example.todolist.security.oauth.model.AuthEntity;
import com.example.todolist.security.user.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final CustomOAuthUserService userService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @PostMapping("/authorization/todo-list")
    public void getToken(@RequestBody AuthEntity authRequest,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        try {
            String email = authRequest.email();
            String password = authRequest.password();

            CustomUserDetails user = userService.loadUserWithEmailAndPassword(email, password);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        } catch (InsufficientAuthenticationException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        }

    }
}