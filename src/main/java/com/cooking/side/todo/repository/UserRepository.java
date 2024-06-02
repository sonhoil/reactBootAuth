package com.cooking.side.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooking.side.todo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    
    // 추가된 메서드
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    
    Optional<User> findByEmail(String email);
    
    
}