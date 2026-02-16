package com.api.card.cardsAPI.controller;

import com.api.card.cardsAPI.service.CardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCard() throws Exception {

        var json = """
                {
                    "number": "1234567890123456",
                    "holderName": "Ruann Lima"
                }
                """;

        mockMvc.perform(post("/cards")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUploadFileAndProcessCards() throws Exception {

        String content = "4916533139859230;Ruann\n" + "1111222233334444;Timoteo";

        MockMultipartFile file =
                new MockMultipartFile(
                        "fileCards",
                        "cards.txt",
                        "text/plain",
                        content.getBytes(StandardCharsets.UTF_8)
                );

        mockMvc.perform(multipart("/cards/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Arquivo processado com sucesso"));

        Mockito.verify(service, Mockito.times(2))
                .saveCard(anyString(), anyString());
    }

    @Test
    void shouldReturnCardIdWhenFound() throws Exception {

        UUID id = UUID.randomUUID();

        when(service.findCard("4916533139859230")).thenReturn(id);

        var json = """
                {
                    "number": "4916533139859230"
                }
                """;

        mockMvc.perform(post("/cards/search")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value(id.toString()));
    }
}
