package com.api.card.cardsAPI.repository;

import com.api.card.cardsAPI.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByHashNumber(String hashNumber);
    Optional<Card> findByHashNumber(String hashNumber);
}
