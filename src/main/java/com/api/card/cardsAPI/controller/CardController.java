package com.api.card.cardsAPI.controller;

import com.api.card.cardsAPI.domain.dto.CardRequest;
import com.api.card.cardsAPI.domain.dto.CardSearchpRequest;
import com.api.card.cardsAPI.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CardRequest request) {
        service.saveCard(request.number(), request.holderName());
        return ResponseEntity.status(201).body("Cartão salvo com sucesso");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("fileCards") MultipartFile file) {

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                service.saveCard(parts[0], parts[1]);
            }

            return ResponseEntity.ok("Arquivo processado com sucesso");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar arquivo");
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> lookup(@RequestBody CardSearchpRequest request) {

        UUID id = service.findCard(request.number());

        return ResponseEntity.ok(Map.of("cardId", id));
    }
}
