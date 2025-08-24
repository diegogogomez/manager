package com.gtc.manager.infrastructure.adapters;

import com.gtc.manager.application.ports.out.MessageGateway;
import com.gtc.manager.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageAdapter implements MessageGateway {
    
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQMessageAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void sendMessageId(String messageId) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                messageId
        );
        System.out.println("📤 ID de mensaje enviado: " + messageId);
    }
}

