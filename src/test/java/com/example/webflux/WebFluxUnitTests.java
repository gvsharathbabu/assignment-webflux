package com.example.webflux;

import com.example.webflux.controller.TitleController;
import com.example.webflux.entity.Titles;
import com.example.webflux.service.TitleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(TitleController.class)
public class WebFluxUnitTests {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private TitleService titleService;

    @Test
    public void getTitles() {

        Mockito.when(titleService.getByTypeAndCountry(Mockito.anyString(),
                Mockito.anyString())).thenReturn(Flux.just(new Titles()));

        testClient
                .get()
                .uri("/get-titles?type=" + "TV Show" + "&country=" + "United States")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

}
