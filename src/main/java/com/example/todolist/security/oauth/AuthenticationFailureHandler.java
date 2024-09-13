package com.example.todolist.security.oauth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                          HttpServletResponse response,
                                          AuthenticationException exception) throws IOException {
        logAuthenticationFailure(exception);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setContentType("application/json");

        response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        response.getWriter().flush();
   }

private void logAuthenticationFailure(AuthenticationException exception) {
    if (log.isTraceEnabled()) {
        log.trace("Authentication failed: {}", exception.getMessage());
    } else {
        log.debug("Sending 401 Unauthorized error");
    }
}
}
