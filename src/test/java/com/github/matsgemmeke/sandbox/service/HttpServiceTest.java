package com.github.matsgemmeke.sandbox.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.matsgemmeke.sandbox.model.Competition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpServiceTest {

    private RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    public void setUp() {
        this.restTemplateBuilder = mock(RestTemplateBuilder.class);
    }

    @Test
    public void returnsGenericObjectWhenUsingGetRequest() {
        Competition competition = new Competition();
        ResponseEntity<Competition> response = new ResponseEntity<>(competition, HttpStatus.OK);
        RestTemplate restTemplate = mock(RestTemplate.class);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Competition.class))).thenReturn(response);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        HttpService httpService = new HttpService(restTemplateBuilder);

        Competition result = httpService.get("uri", Competition.class);

        assertThat(result).isEqualTo(competition);
    }
}
