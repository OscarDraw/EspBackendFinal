package com.dh.catalog.controller.dto;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineCatalogDTO {
    public String genre;
    public List<MovieFeign.MovieDto> movies;
    public List<SerieFeign.SerieDTO> series;
}
