package com.codigo.patron_builder.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TipoCarro {
    private Long id;
    private String tipo;
}
