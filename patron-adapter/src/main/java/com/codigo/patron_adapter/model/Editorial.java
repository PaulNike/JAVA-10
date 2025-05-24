package com.codigo.patron_adapter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Editorial {
    private String nombre;
    private String direccion;
    private String ciudad;
    private Contacto contacto;
}
