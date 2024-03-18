package com.example.webflux.repository;

import com.example.webflux.entity.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, Integer> {

    Flux<Movie> findByNameAndCountry(String name, String country);

}