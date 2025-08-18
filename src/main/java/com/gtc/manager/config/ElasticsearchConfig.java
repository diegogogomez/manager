package com.gtc.manager.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

	@Value("${elasticsearch.host:localhost}")
	private String host;

	@Value("${elasticsearch.port:9200}")
	private int port;

	@Value("${elasticsearch.username:elastic}")
	private String username;

	@Value("${elasticsearch.password:changeme}")
	private String password;

	@Value("${elasticsearch.scheme:http}")
	private String scheme;

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public RestClient restClient() {
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
			new UsernamePasswordCredentials(username, password));

		return RestClient.builder(new HttpHost(host, port, scheme))
				.setHttpClientConfigCallback(httpClientBuilder ->
					httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
				.build();
	}

	@Bean
	public ElasticsearchTransport elasticsearchTransport() {
		// Crear un JacksonJsonpMapper con el ObjectMapper configurado
		JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);
		return new RestClientTransport(restClient(), jsonpMapper);
	}

	@Bean
	public ElasticsearchClient elasticsearchClient() {
		return new ElasticsearchClient(elasticsearchTransport());
	}
}
