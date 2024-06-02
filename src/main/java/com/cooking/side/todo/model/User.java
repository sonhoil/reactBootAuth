package com.cooking.side.todo.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;  // 추가된 필드
    private String password;
    private String role = "ROLE_USER"; // 기본 값 설정
    private String provider;
    private String providerId;
    private String nickname;

    @OneToMany(mappedBy = "user")
    private Set<Todo> todos;
}