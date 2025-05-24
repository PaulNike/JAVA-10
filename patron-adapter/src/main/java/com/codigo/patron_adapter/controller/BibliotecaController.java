package com.codigo.patron_adapter.controller;

import com.codigo.patron_adapter.adapter.AdapterBiblioteca;
import com.codigo.patron_adapter.model.Libro;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/adapter")
public class BibliotecaController {
    private final AdapterBiblioteca biblioteca;

    public BibliotecaController(AdapterBiblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> obtenerLibro(@PathVariable Integer id){
        return ResponseEntity.ok(biblioteca.obtenerDetalle(id));
    }

    @GetMapping(value = "/v2/{id}")
    public ResponseEntity<Libro> obtenerLibro2(@PathVariable Integer id) throws JsonProcessingException {
        return ResponseEntity.ok(biblioteca.obtenerDetalle2(id));
    }
}
