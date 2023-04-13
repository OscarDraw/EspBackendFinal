package com.dh.movie;

import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class ApiMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMovieApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(MovieRepository repository) {
        return (args) -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }

            repository.save(new Movie(null, "El Conjuro", "Terror", "www.netflix.com"));
            repository.save(new Movie(null, "It", "Terror", "www.netflix.com"));
            repository.save(new Movie(null, "¿Qué Pasó Ayer?", "Comedia", "www.netflix.com"));
            repository.save(new Movie(null, "Interestelar", "Ficción", "www.netflix.com"));
            repository.save(new Movie(null, "Blade Runner 2049", "Ficción", "www.netflix.com"));
            repository.save(new Movie(null, "La Llegada", "Ficción", "www.netflix.com"));
            repository.save(new Movie(null, "Zombieland", "Comedia", "www.netflix.com"));

        };
    }

}
