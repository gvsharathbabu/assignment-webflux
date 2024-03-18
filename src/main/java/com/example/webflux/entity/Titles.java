package com.example.webflux.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "titles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Titles {

    @Id
    @Field("show_id")
    private String showId;
    private String type;
    private String title;
    private String director;
    private String cast;
    private String country;
    private String date_added;
    private String release_year;
    private String rating;
    private String duration;
    private String listed_in;
    private String description;
}
