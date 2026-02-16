package com.api.card.cardsAPI.service;

import com.api.card.cardsAPI.domain.entity.Card;
import com.api.card.cardsAPI.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CryptoService cryptoService;

    public CardService(CardRepository cardRepository,
                       CryptoService cryptoService) {
        this.cardRepository = cardRepository;
        this.cryptoService = cryptoService;
    }

    public void saveCard(String number, String holder) {

        String hash = hash(number);

        if (cardRepository.existsByHashNumber(hash)) {
            throw new RuntimeException("Cartão já cadastrado");
        }

        String encrypted = cryptoService.encrypt(number);

        Card card = Card.builder()
                .encryptedNumber(encrypted)
                .hashNumber(hash)
                .holderName(holder)
                .build();

        cardRepository.save(card);
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public UUID findCard(String number) {

        String hash = cryptoService.hashNumber(number);

        return cardRepository.findByHashNumber(hash)
                .map(Card::getId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
    }

}
