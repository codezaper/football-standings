package com.publicis.football.standings.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import java.time.Duration;


@Configuration
@Slf4j
public class RestClientConfig {
    @Value("${api.football.key}")
    private String apiKey;

    @Value("${api.football.apiSecretKey}")
    private String apiSecretKey;

    @Value("${api.football.apiSecretSalt}")
    private String apiSecretSalt;

    @Value("${api.football.baseUrl}")
    private String baseUrl;

    @Value("${api.football.timeout.connection:60L}")
    private Long connectTimeout;

    @Value("${api.football.timeout.read:60L}")
    private Long readTimeout;

    @Bean
    public RestClient restClient() {
        // Setting Read and Connection Timeout
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(connectTimeout));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(readTimeout));

        return RestClient.builder()
                .baseUrl(baseUrl+"?APIkey="+EncryptionUtil.decrypt(apiKey,apiSecretKey,apiSecretSalt))
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .requestFactory(simpleClientHttpRequestFactory)
                .build();
    }
}
