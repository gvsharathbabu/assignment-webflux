package com.example.webflux.repository;

import com.example.webflux.entity.Titles;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TitleRepository extends ReactiveMongoRepository<Titles, String> {

    Flux<Titles> findByTypeAndCountry(String type, String country);

    Mono<Titles> findByShowIdOrTitle(String showId, String title);

}

