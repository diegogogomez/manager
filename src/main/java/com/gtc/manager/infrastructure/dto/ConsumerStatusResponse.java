package com.gtc.manager.infrastructure.dto;

import java.time.LocalDateTime;

public class ConsumerStatusResponse {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    
    public ConsumerStatusResponse() {}
    
    public ConsumerStatusResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

