package com.github.matsgemmeke.sandbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class HttpService {

    private static final String API_URI = "http://localhost:8080";

    private final RestTemplateBuilder restTemplateBuilder;

    public <T> T get(String uri, Class<T> theClass) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<T> res = restTemplate.exchange(API_URI + uri, HttpMethod.GET, entity, theClass);

        return res.getBody();
    }
}
