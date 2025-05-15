package com.codigo.webClient.response;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReniecResponse {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
