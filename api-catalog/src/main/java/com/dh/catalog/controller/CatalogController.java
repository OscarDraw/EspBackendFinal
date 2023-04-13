package com.dh.catalog.controller;

import com.dh.catalog.controller.dto.OfflineCatalogDTO;
import com.dh.catalog.controller.dto.OnlineCatalogDTO;
import com.dh.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	@Autowired
	public CatalogService catalogService;

	@GetMapping("/online/{genre}")
	ResponseEntity<OnlineCatalogDTO> getGenreOnline(@PathVariable String genre, HttpServletResponse response) {
		return ResponseEntity.ok(catalogService.getCatalogByGenreOnline(genre));
	}

	@GetMapping("/offline/{genre}")
	ResponseEntity<OfflineCatalogDTO> getGenreOffline(@PathVariable String genre, HttpServletResponse response) {
		return ResponseEntity.ok(catalogService.getCatalogByGenreOffline(genre));
	}

}
