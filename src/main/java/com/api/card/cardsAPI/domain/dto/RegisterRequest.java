package com.api.card.cardsAPI.domain.dto;

public record RegisterRequest(
        String username,
        String password
) {}