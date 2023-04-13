package com.dh.catalog.service;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.controller.dto.OfflineCatalogDTO;
import com.dh.catalog.controller.dto.OnlineCatalogDTO;
import com.dh.catalog.event.NewMovieEventConsumer;
import com.dh.catalog.event.NewSerieEventConsumer;
import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    @Autowired
    MovieFeign movieFeign;
    @Autowired
    SerieFeign serieFeign;
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    MovieRepository movieRepository;

    /**
     * Online
     **/
    @CircuitBreaker(name = "showCatalog", fallbackMethod = "showCatalogFallBack")
    @Retry(name = "retryCatalog")
    public OnlineCatalogDTO getCatalogByGenreOnline(String genre) {
        List<MovieFeign.MovieDto> moviesResponse = movieFeign.getMovieByGenre(genre);
        List<SerieFeign.SerieDTO> seriesResponse = serieFeign.getSerieByGenre(genre);
        OnlineCatalogDTO catalog = new OnlineCatalogDTO();
        catalog.setGenre(genre);
        catalog.setMovies(moviesResponse);
        catalog.setSeries(seriesResponse);
        return catalog;
    }
    public OnlineCatalogDTO showCatalogFallBack(String genre, Throwable t) {
        OnlineCatalogDTO onlineCatalogDTO = new OnlineCatalogDTO();
        OfflineCatalogDTO offlineCatalogDTO;
        offlineCatalogDTO = getCatalogByGenreOffline(genre);
        onlineCatalogDTO.setGenre(genre);
        onlineCatalogDTO.setSeries(convertSerieToSerieDTO(offlineCatalogDTO.getSeries()));
        onlineCatalogDTO.setMovies(convertMovieToMovieDTO(offlineCatalogDTO.getMovies()));
        return onlineCatalogDTO;
    }

    /**
     * Offline
     **/
    public OfflineCatalogDTO getCatalogByGenreOffline(String genre) {
        List<Movie> moviesResponse = movieRepository.findByGenre(genre);
        List<Serie> seriesResponse = serieRepository.findByGenre(genre);
        OfflineCatalogDTO catalog = new OfflineCatalogDTO();
        catalog.setGenre(genre);
        catalog.setMovies(moviesResponse);
        catalog.setSeries(seriesResponse);
        return catalog;
    }

    public String createMovie(NewMovieEventConsumer.MessageMovie message) {
        Movie movie = new Movie();
        BeanUtils.copyProperties(message, movie);
        movieRepository.save(movie);
        return movie.getId();
    }

    public String createSerie(NewSerieEventConsumer.MessageSerie message) {
        Serie serie = new Serie();
        BeanUtils.copyProperties(message, serie);
        serieRepository.save(serie);
        return serie.getId();
    }

    private static List<SerieFeign.SerieDTO> convertSerieToSerieDTO(List<Serie> series) {
        List<SerieFeign.SerieDTO> seriesDTO = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Serie serie: series)
            seriesDTO.add(objectMapper.convertValue(serie, SerieFeign.SerieDTO.class));
        return seriesDTO;
    }

    private static List<MovieFeign.MovieDto> convertMovieToMovieDTO(List<Movie> movies) {
        List<MovieFeign.MovieDto> moviesDTO = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Movie movie: movies)
            moviesDTO.add(objectMapper.convertValue(movie, MovieFeign.MovieDto.class));
        return moviesDTO;
    }

}
