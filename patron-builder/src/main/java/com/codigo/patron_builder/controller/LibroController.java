package com.codigo.patron_builder.controller;

import com.codigo.patron_builder.model.Carro;
import com.codigo.patron_builder.model.Libro;
import com.codigo.patron_builder.model.TipoCarro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/builder")
public class LibroController {

    @GetMapping("/librofull")
    public ResponseEntity<Libro> obtenerLibroManual(){
        return ResponseEntity.ok(new Libro.Builder()
                .titulo("El conde de Montecristo")
                .autor("Alexander Dumas")
                //.fechaPublicacion("25/05/2025")
                //.isbn("xxxxxxxxxxxxx")
                //.estado("ACTIVO")
                .build());
    }

    @GetMapping("/carrofull")
    public ResponseEntity<Carro> obtenerCarro(){
        return ResponseEntity.ok(Carro.builder()
                        .id(1L)
                        .marca("geely")
                        .modelo("SUV")
                        .anio(2025)
                        .tipoCarro(TipoCarro.builder()
                                .id(2L)
                                .tipo("AUTOMATICO")
                                .build())
                        .build());
    }
}
