package com.gtc.manager.infrastructure.controllers;

import com.gtc.manager.application.ports.out.MessageConsumerGateway;
import com.gtc.manager.infrastructure.dto.ConsumerStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {
    
    private final MessageConsumerGateway messageConsumerGateway;
    
    public ConsumerController(MessageConsumerGateway messageConsumerGateway) {
        this.messageConsumerGateway = messageConsumerGateway;
    }
    
    @PostMapping("/start")
    public ResponseEntity<ConsumerStatusResponse> startConsumer() {
        messageConsumerGateway.startConsuming();
        return ResponseEntity.ok(new ConsumerStatusResponse("STARTED", "Consumer iniciado exitosamente"));
    }
    
    @PostMapping("/stop")
    public ResponseEntity<ConsumerStatusResponse> stopConsumer() {
        messageConsumerGateway.stopConsuming();
        return ResponseEntity.ok(new ConsumerStatusResponse("STOPPED", "Consumer detenido exitosamente"));
    }
    
    @GetMapping("/status")
    public ResponseEntity<ConsumerStatusResponse> getStatus() {
        return ResponseEntity.ok(new ConsumerStatusResponse("ACTIVE", "Consumer está activo y escuchando mensajes"));
    }
}
