package com.example.webflux;

import com.example.webflux.entity.Titles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class WebFluxApplicationTests {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void getTitles() {
        testClient
                .get()
                .uri("/get-titles?type=" + "TV Show" + "&country=" + "United States")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Titles.class)
                .hasSize(2);
    }

}
