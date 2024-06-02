package com.cooking.side.todo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooking.side.todo.model.User;
import com.cooking.side.todo.service.UserService;
import com.cooking.side.todo.util.JwtUtils;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public Map<String, Object> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        return response;
    }

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, String> registerRequest) {
        System.out.println("Register request received with data: " + registerRequest);
        String email = registerRequest.get("email");
        String nickname = registerRequest.get("nickname");
        String providerId = registerRequest.get("providerId");

        Optional<User> existingUser = userService.findByUsername(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with email: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(email); // 이메일을 username으로 사용
        user.setNickname(nickname);
        user.setProvider("kakao");
        user.setProviderId(providerId);

        // 기본 비밀번호 설정
        String defaultPassword = "default_password";
        user.setPassword(passwordEncoder.encode(defaultPassword));

        userService.saveUser(user);

        System.out.println("User saved successfully: " + user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, defaultPassword));

        System.out.println("Authentication successful for user: " + email);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        System.out.println("JWT generated and response created: " + response);
        return response;
    }
}
