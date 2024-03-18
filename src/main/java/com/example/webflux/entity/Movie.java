package com.example.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Year;

@Document(collection = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private String name;
    private String releaseYear;
    private String country;
}
