package com.api.card.cardsAPI.domain.dto;

public record AuthRequest(
        String username,
        String password
) {}

