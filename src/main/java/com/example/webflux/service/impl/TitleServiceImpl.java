package com.example.webflux.service.impl;

import com.example.webflux.entity.Titles;
import com.example.webflux.repository.TitleRepository;
import com.example.webflux.service.TitleService;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleRepository titleRepository;


    /**
     * This method was used to load the multipart file from the request. But now its not used.
     *
     * @param multipartFile
     * @return
     */
    @Override
    public Mono<String> saveList(MultipartFile multipartFile) {
        log.info("Entered saveList...");
        List<Titles> titlesDTOList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            CSVReader csvReader = new CSVReader(bufferedReader);

            String[] nextRecord;
            bufferedReader.readLine();

            while ((nextRecord = csvReader.readNext()) != null) {
                Titles titles = getTitles(nextRecord);
                titlesDTOList.add(titles);
            }
            return titleRepository.saveAll(titlesDTOList).then(Mono.just("Data loaded successfully!"));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    /**
     * This method will return list of titles based on type and country
     *
     * @param type
     * @param country
     * @return
     */
    @Override
    public Flux<Titles> getByTypeAndCountry(String type, String country) {
        log.info("Entered getByTypeAndCountry : {} {}", type, country);
        return titleRepository.findByTypeAndCountry(type, country);
    }

    /**
     * This method will updated the release year by taking either show_id or title
     *
     * @param showId
     * @param title
     * @param year
     * @return
     */
    @Override
    public Mono<String> getTitleByShowIdOrTitle(String showId, String title, String year) {
        log.info("Entered getTitleByShowIdOrTitle : {} {} {}", showId, title, year);
        return titleRepository.findByShowIdOrTitle(showId, title)
                .flatMap(t -> {
                    t.setRelease_year(year);
                    return titleRepository.save(t).thenReturn("Update Success");
                });
    }

    /**
     * This method will return all data from db
     * @return
     */
    @Override
    public Flux<Titles> getData() {
        log.info("getData()");
        return titleRepository.findAll();
    }

    /**
     * Following method is used to map the array with of strings with titles.
     *
     * @param nextRecord
     * @return
     */
    private Titles getTitles(String[] nextRecord) {
        Titles titles = new Titles();
        titles.setShowId(nextRecord[0]);
        titles.setType(nextRecord[1]);
        titles.setTitle(nextRecord[2]);
        titles.setDirector(nextRecord[3]);
        titles.setCast(nextRecord[4]);
        titles.setCountry(nextRecord[5]);
        titles.setDate_added(nextRecord[6]);
        titles.setRelease_year(nextRecord[7]);
        titles.setRating(nextRecord[8]);
        titles.setDuration(nextRecord[9]);
        titles.setListed_in(nextRecord[10]);
        titles.setDescription(nextRecord[11]);
        return titles;
    }


    /**
     * This method is used to pick the csv file from the class path and load the data into mongo db
     *
     * @return
     */
    @Override
    public Mono<Void> loadDataToDB() {
        return Flux.using(
                () -> new CSVReader(new FileReader(new ClassPathResource("netflix_titles.csv").getFile())),
                reader -> Flux.fromIterable(reader)
                        .skip(1)
                        .map(this::getTitles)
                        .flatMap(titleRepository::save),
                reader -> {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to close CSV file", e);
                    }
                }).then();
    }
}
