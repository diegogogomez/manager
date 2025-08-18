package com.gtc.manager.application.ports.in;

@FunctionalInterface
public interface MessageUseCase {
    void sendMessage(String message);
}

