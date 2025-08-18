package com.gtc.manager.infrastructure.controllers;

import com.gtc.manager.application.ports.in.MessageUseCase;
import com.gtc.manager.infrastructure.dto.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    private final MessageUseCase messageUseCase;
    
    public MessageController(MessageUseCase messageUseCase) {
        this.messageUseCase = messageUseCase;
    }
    
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        messageUseCase.sendMessage(request.getMessage());
        return ResponseEntity.ok("Mensaje enviado exitosamente: " + request.getMessage());
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Message Service is running!");
    }
}
