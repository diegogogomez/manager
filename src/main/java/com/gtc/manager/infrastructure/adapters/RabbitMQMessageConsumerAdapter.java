package com.gtc.manager.infrastructure.adapters;

import com.gtc.manager.application.ports.in.MessageConsumerUseCase;
import com.gtc.manager.application.ports.out.MessageConsumerGateway;
import com.gtc.manager.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageConsumerAdapter implements MessageConsumerGateway {
    
    private final MessageConsumerUseCase messageConsumerUseCase;
    
    public RabbitMQMessageConsumerAdapter(MessageConsumerUseCase messageConsumerUseCase) {
        this.messageConsumerUseCase = messageConsumerUseCase;
    }
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("📨 Mensaje recibido de la cola: " + message);
        
        try {
            // Delegar el procesamiento al caso de uso
            messageConsumerUseCase.processMessage(message);
        } catch (Exception e) {
            System.err.println("❌ Error procesando mensaje: " + message);
            System.err.println("Error: " + e.getMessage());
            // Aquí podrías implementar estrategias de reintento o dead letter queue
        }
    }
    
    @Override
    public void startConsuming() {
        System.out.println("🚀 Consumer iniciado - Escuchando en cola: " + RabbitMQConfig.QUEUE_NAME);
    }
    
    @Override
    public void stopConsuming() {
        System.out.println("⏹️ Consumer detenido");
    }
}
