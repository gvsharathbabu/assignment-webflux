package com.example.webflux.controller;

import com.example.webflux.entity.Titles;
import com.example.webflux.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TitleController {

    @Autowired
    private TitleService titleService;


    @PostMapping("/save-titles")
    public Mono<String> uploadFile() {
        return titleService.loadDataToDB()
                .thenReturn("CSV file processed and saved to MongoDB.")
                .onErrorResume(e -> Mono.just("Failed to process and save CSV file: " + e.getMessage()))
                .onErrorReturn("Internal Server Error")
                .log();
    }

    @GetMapping("/getAll")
    public Flux<Titles> getAllData() {
        return titleService.getData();
    }

    @GetMapping("/get-titles")
    public Flux<Titles> getTitles(@RequestParam String type, @RequestParam String country) {
        return titleService.getByTypeAndCountry(type, country).doOnError(
                throwable -> {
                    throw new RuntimeException("Error");
                }
        );
    }

    @PutMapping("/update-title")
    public Mono<String> updateTitle(@RequestParam String id, @RequestParam String title, @RequestParam String year) {
        return titleService.getTitleByShowIdOrTitle(id, title, year).switchIfEmpty(Mono.just("No data found!"));
    }

}