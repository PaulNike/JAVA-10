package com.codigo.patron_adapter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Libro {
    private int id;
    private String titulo;
    private String genero;
    private int anio;
    private Editorial editorial;
    private List<Autor> autores;
    private Ubicacion ubicacion;
}
