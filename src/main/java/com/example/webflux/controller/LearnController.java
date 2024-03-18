package com.example.webflux.controller;

import com.example.webflux.dto.DataDTO;
import com.example.webflux.entity.Movie;
import com.example.webflux.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class LearnController {

    @Autowired
    private LearnService learnService;

    @GetMapping("/data")
    public List<String> getData() {
        return List.of("THIS", "IS", "WEBFLUX");
    }

    @GetMapping("/data-flex")
    public Flux<DataDTO> getDataFlex() {
        return Flux.fromIterable(learnService.getData());
    }

    @PostMapping("/save-movie")
    public Mono<Movie> saveMovie(@RequestBody Movie movie) {
        return learnService.saveMovie(movie);
    }

    @GetMapping("/get-movie")
    public Flux<Movie> getMovie(@RequestBody Movie movie) {
        return learnService.getMovie(movie);
    }

//    @PostMapping("/save-list")
//    public Mono<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        return learnService.saveList(file);
//    }
//
//    @PostMapping("/save-list-stream")
//    public Mono<String> uploadFileStream(@RequestParam("file") Flux<DataBuffer> dataBufferFlux) {
//        return learnService.saveListStream(dataBufferFlux);
//    }


//
//    @PostMapping
//    public ResponseEntity<?> uploadFile(@RequestParam("csv") MultipartFile file) {
//        return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.OK);
//    }
//
//    @PostMapping("/upload")
//    public List<Titles> uploadFileFlex(@RequestParam("csv") MultipartFile file) {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            return br.lines()
//                    .skip(1)
//                    .map(line -> {
//                        String[] titles = line.split(",");
//                        return new Titles(titles[0], titles[1], titles[2], titles[3],
//                                titles[4], titles[5], titles[6], titles[7],
//                                titles[8], titles[9], titles[10], titles[11]);
//                    }).toList();
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    @PostMapping("/upload1")
//    public Flux<Titles> uploadFileFlex1(@RequestParam("csv") MultipartFile file) {
//        return Flux.create(t -> {
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//                List<Titles> shows = br.lines()
//                        .skip(1)
//                        .map(line -> line.split(","))
//                        .map(titles -> new Titles(titles[0], titles[1], titles[2], titles[3],
//                                titles[4], titles[5], titles[6], titles[7],
//                                titles[8], titles[9], titles[10], titles[11]))
//                        .toList();
//
//                shows.forEach(t::next);
//                t.complete();
//            } catch (Exception e) {
//                throw new RuntimeException(e.getMessage());
//            }
//        });
//    }
}
