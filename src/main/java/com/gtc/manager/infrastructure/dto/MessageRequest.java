package com.gtc.manager.infrastructure.dto;

public class MessageRequest {
    private String message;
    
    // Constructor por defecto
    public MessageRequest() {}
    
    // Constructor con parámetros
    public MessageRequest(String message) {
        this.message = message;
    }
    
    // Getters y Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}

