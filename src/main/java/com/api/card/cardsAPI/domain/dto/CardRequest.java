package com.api.card.cardsAPI.domain.dto;

public record CardRequest(
        String number,
        String holderName
) {}