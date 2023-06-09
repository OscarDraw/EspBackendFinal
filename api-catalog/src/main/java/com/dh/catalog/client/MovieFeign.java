package com.dh.catalog.client;

import com.dh.catalog.config.LoadBalancerConfiguration;
import lombok.*;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="api-movie")
@LoadBalancerClient(value = "api-movie", configuration = LoadBalancerConfiguration.class)
public interface MovieFeign {

	@GetMapping("/api/v1/movies/{genre}")
	List<MovieDto> getMovieByGenre(@PathVariable (value = "genre") String genre);

	@Data
	class MovieDto{
		private Long id;
		private String name;
		private String genre;
		private String urlStream;
	}

}
