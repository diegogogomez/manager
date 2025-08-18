package com.gtc.manager.infrastructure.controllers;

import com.gtc.manager.domain.entities.MessageDocument;
import com.gtc.manager.infrastructure.services.ElasticsearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/elasticsearch")
public class ElasticsearchQueryController {
    
    private final ElasticsearchService elasticsearchService;
    
    public ElasticsearchQueryController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }
    
    /**
     * Obtiene todos los mensajes guardados
     */
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDocument>> getAllMessages() {
        try {
            List<MessageDocument> messages = elasticsearchService.findAllMessages();
            return ResponseEntity.ok(messages);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Busca mensajes por tipo
     */
    @GetMapping("/messages/type/{type}")
    public ResponseEntity<List<MessageDocument>> getMessagesByType(@PathVariable String type) {
        try {
            List<MessageDocument> messages = elasticsearchService.findMessagesByType(type);
            return ResponseEntity.ok(messages);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Busca mensajes por contenido
     */
    @GetMapping("/messages/search")
    public ResponseEntity<List<MessageDocument>> searchMessages(@RequestParam String content) {
        try {
            List<MessageDocument> messages = elasticsearchService.findMessagesByContent(content);
            return ResponseEntity.ok(messages);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Obtiene el conteo total de mensajes
     */
    @GetMapping("/messages/count")
    public ResponseEntity<Long> getMessageCount() {
        try {
            long count = elasticsearchService.countMessages();
            return ResponseEntity.ok(count);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

