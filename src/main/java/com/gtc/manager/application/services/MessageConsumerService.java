package com.gtc.manager.application.services;

import com.gtc.manager.application.ports.in.MessageConsumerUseCase;
import com.gtc.manager.infrastructure.services.ElasticsearchService;
import com.gtc.manager.domain.entities.MessageDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageConsumerService implements MessageConsumerUseCase {
    
    private final ElasticsearchService elasticsearchService;
    
    @Autowired
    public MessageConsumerService(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }
    
    @Override
    public void processMessage(String message) {
        System.out.println("🔄 Procesando mensaje: " + message);
        
        try {
            // Crear documento del mensaje
            MessageDocument messageDoc = new MessageDocument(message, "CONSUMED");
            
            // Guardar en Elasticsearch
            String documentId = elasticsearchService.saveMessage(messageDoc);
            System.out.println("✅ Mensaje procesado y guardado con ID: " + documentId);
            
        } catch (IOException e) {
            System.err.println("❌ Error procesando mensaje: " + e.getMessage());
            throw new RuntimeException("Error procesando mensaje", e);
        }
    }
}

