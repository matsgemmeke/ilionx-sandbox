package com.github.matsgemmeke.sandbox.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getIndex() {
        ResponseEntity<String> res = template.getForEntity("/user", String.class);

        assertThat(res.getBody()).isEqualTo("[]");
    }
}
