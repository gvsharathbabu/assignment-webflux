package com.example.webflux.service;

import com.example.webflux.dto.DataDTO;
import com.example.webflux.entity.Movie;
import com.example.webflux.entity.Titles;
import com.example.webflux.repository.MovieRepository;
import com.example.webflux.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LearnService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TitleRepository titleRepository;

    public List<DataDTO> getData() {
        return List.of(new DataDTO("THIS"),
                new DataDTO("IS"),
                new DataDTO("WEBFLUX"));
    }

    public Mono<Movie> saveMovie(Movie movie) {
        Mono<Movie> savedMovie = movieRepository.save(movie);
        System.out.println("data : {} " + movie.getName() + " : " + movie.getReleaseYear());
        return savedMovie;
    }

    public Flux<Movie> getMovie(Movie movie) {
        return movieRepository.findByNameAndCountry(movie.getName(), movie.getCountry());
    }

    public Mono<String> saveList(MultipartFile multipartFile) {

        List<Titles> titlesDTOList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] titles = line.split(",");
                Titles titlesDTO = new Titles(titles[0], titles[1], titles[2], titles[3],
                        titles[4], titles[5], titles[6], titles[7],
                        titles[8], titles[9], titles[10], titles[11]);
                titlesDTOList.add(titlesDTO);
            }
            return titleRepository.saveAll(titlesDTOList).then(Mono.just("Data loaded successfully!"));
        } catch (Exception e) {
            return Mono.error(e);
            //throw new RuntimeException("Error : " + e.getMessage());
        }
    }

    public Mono<String> saveListStream(Flux<DataBuffer> dataBufferFlux) {

        return dataBufferFlux
                .collectList()
                .flatMap(df -> {

                    List<Titles> titlesDTOList = new ArrayList<>();
                    StringBuilder sb = new StringBuilder();
                    for (DataBuffer dataBuffer : df) {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        sb.append(new String(bytes));
                    }

                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(sb.toString().getBytes())))) {
                        String line;
                        bufferedReader.readLine();

                        while ((line = bufferedReader.readLine()) != null) {
                            String[] titles = line.split(",");
                            Titles titlesDTO = new Titles(titles[0], titles[1], titles[2], titles[3],
                                    titles[4], titles[5], titles[6], titles[7],
                                    titles[8], titles[9], titles[10], titles[11]);
                            titlesDTOList.add(titlesDTO);
                        }
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                    return titleRepository.saveAll(titlesDTOList).then(Mono.just("Data loaded successfully!"));
                });
    }
}
