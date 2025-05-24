package com.codigo.patron_builder.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Carro {
    private Long id;
    private String modelo;
    private Integer anio;
    private String marca;
    private TipoCarro tipoCarro;

}
