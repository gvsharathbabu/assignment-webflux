package com.example.webflux.service;

import com.example.webflux.entity.Titles;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface TitleService {

    Mono<String> saveList(MultipartFile multipartFile);

    Flux<Titles> getByTypeAndCountry(String type, String country);

    Mono<String> getTitleByShowIdOrTitle(String showId, String title, String year);

    Flux<Titles> getData();

    Mono<Void> loadDataToDB();

}
