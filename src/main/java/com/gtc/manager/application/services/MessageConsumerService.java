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
    public void processMessage(String messageId) {
        System.out.println("🔄 Procesando ID de mensaje: " + messageId);
        
        try {
            // Obtener el mensaje completo desde Elasticsearch usando el ID
            MessageDocument originalMessage = elasticsearchService.findMessageById(messageId);
            System.out.println("📄 Mensaje original recuperado: " + originalMessage.getMessage());
            
            // Aquí puedes implementar la lógica de negocio para procesar el mensaje
            // Por ejemplo: validaciones, transformaciones, envío a otros servicios, etc.
            System.out.println("✅ Mensaje procesado exitosamente");
            
        } catch (IOException e) {
            System.err.println("❌ Error procesando mensaje con ID " + messageId + ": " + e.getMessage());
            throw new RuntimeException("Error procesando mensaje", e);
        }
    }
}

