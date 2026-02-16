package com.api.card.cardsAPI.service;

import com.api.card.cardsAPI.domain.dto.RegisterRequest;
import com.api.card.cardsAPI.domain.entity.User;
import com.api.card.cardsAPI.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {

        if (repository.existsByUsername(request.username())) {
            throw new RuntimeException("Usuário já existe");
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        repository.save(user);
    }
}