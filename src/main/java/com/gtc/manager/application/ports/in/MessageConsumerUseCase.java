package com.gtc.manager.application.ports.in;

@FunctionalInterface
public interface MessageConsumerUseCase {
    void processMessage(String message);
}

