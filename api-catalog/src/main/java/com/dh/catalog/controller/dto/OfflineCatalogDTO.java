package com.dh.catalog.controller.dto;

import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfflineCatalogDTO {

    public String genre;
    public List<Movie> movies;
    public List<Serie> series;
}
