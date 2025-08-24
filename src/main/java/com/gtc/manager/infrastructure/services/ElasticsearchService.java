package com.gtc.manager.infrastructure.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.gtc.manager.domain.entities.MessageDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticsearchService {
    
    private static final String INDEX_NAME = "messages";
    
    private final ElasticsearchClient client;
    
    @Autowired
    public ElasticsearchService(ElasticsearchClient client) {
        this.client = client;
    }
    
    /**
     * Guarda un mensaje en Elasticsearch
     */
    public String saveMessage(MessageDocument message) throws IOException {
        IndexResponse response = client.index(i -> i
            .index(INDEX_NAME)
            .document(message)
        );
        return response.id();
    }
    
    /**
     * Obtiene todos los mensajes
     */
    public List<MessageDocument> findAllMessages() throws IOException {
        SearchResponse<MessageDocument> response = client.search(s -> s
            .index(INDEX_NAME)
            .query(q -> q.matchAll(m -> m))
            .size(1000), MessageDocument.class);
        
        return response.hits().hits().stream()
            .map(Hit::source)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca mensajes por tipo
     */
    public List<MessageDocument> findMessagesByType(String type) throws IOException {
        SearchResponse<MessageDocument> response = client.search(s -> s
            .index(INDEX_NAME)
            .query(q -> q.term(t -> t.field("type").value(type)))
            .size(1000), MessageDocument.class);
        
        return response.hits().hits().stream()
            .map(Hit::source)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca mensajes por contenido
     */
    public List<MessageDocument> findMessagesByContent(String content) throws IOException {
        SearchResponse<MessageDocument> response = client.search(s -> s
            .index(INDEX_NAME)
            .query(q -> q.match(m -> m.field("message").query(content)))
            .size(1000), MessageDocument.class);
        
        return response.hits().hits().stream()
            .map(Hit::source)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene el conteo total de mensajes
     */
    public long countMessages() throws IOException {
        CountResponse response = client.count(c -> c.index(INDEX_NAME));
        return response.count();
    }
    
    /**
     * Obtiene un mensaje por su ID
     */
    public MessageDocument findMessageById(String id) throws IOException {
        GetResponse<MessageDocument> response = client.get(g -> g
            .index(INDEX_NAME)
            .id(id), MessageDocument.class);
        
        if (response.found()) {
            return response.source();
        } else {
            throw new IOException("Mensaje no encontrado con ID: " + id);
        }
    }
}
