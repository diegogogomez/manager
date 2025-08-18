package com.gtc.manager.domain.entities;

import java.time.Instant;

public class MessageDocument {
    
    private String id;
    private String message;
    private String type;
    private Instant timestamp;
    private String status;
    
    // Constructor por defecto
    public MessageDocument() {}
    
    // Constructor con parámetros
    public MessageDocument(String message, String type) {
        this.message = message;
        this.type = type;
        this.timestamp = Instant.now();
        this.status = "SENT";
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "MessageDocument{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
