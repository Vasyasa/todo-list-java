package com.example.todolist.security.oauth;

import com.example.todolist.security.user.UserService;
import com.example.todolist.security.user.model.CustomUserDetails;
import com.example.todolist.security.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

@Component
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private CustomUserDetails getCustomUserDetails(UserEntity user) {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUsername(user.getEmail());
        userDetails.setName(user.getEmail());

        return userDetails;
    }

    public CustomUserDetails loadUserWithEmailAndPassword(String email, String password) {
        UserEntity user = getUser(email);

        if (user.getPassword() == null) {
            throw new InsufficientAuthenticationException("Email:" + email);
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return getCustomUserDetails(user);
        } else {
            throw new InsufficientAuthenticationException("Failure. User not known.");
        }
    }

    UserEntity getUser(String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new InsufficientAuthenticationException("Failure. User not known."));
    }

}
