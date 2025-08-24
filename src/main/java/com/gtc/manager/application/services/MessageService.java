package com.gtc.manager.application.services;

import com.gtc.manager.application.ports.in.MessageUseCase;
import com.gtc.manager.application.ports.out.MessageGateway;
import com.gtc.manager.domain.entities.MessageDocument;
import com.gtc.manager.infrastructure.services.ElasticsearchService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageService implements MessageUseCase {
    
    private final MessageGateway messageGateway;
    private final ElasticsearchService elasticsearchService;
    
    public MessageService(MessageGateway messageGateway, ElasticsearchService elasticsearchService) {
        this.messageGateway = messageGateway;
        this.elasticsearchService = elasticsearchService;
    }
    
    @Override
    public void sendMessage(String message) {
        try {
            // Primero guardar mensaje en Elasticsearch
            MessageDocument messageDoc = new MessageDocument(message, "RABBITMQ");
            String documentId = elasticsearchService.saveMessage(messageDoc);
            System.out.println("✅ Mensaje guardado en Elasticsearch con ID: " + documentId);
            
            // Luego enviar solo el ID al tópico
            messageGateway.sendMessageId(documentId);
            
        } catch (IOException e) {
            System.err.println("❌ Error guardando mensaje en Elasticsearch: " + e.getMessage());
        }
    }
}
