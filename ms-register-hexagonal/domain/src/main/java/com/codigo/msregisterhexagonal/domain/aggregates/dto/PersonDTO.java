package com.codigo.msregisterhexagonal.domain.aggregates.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;
    private String numDocPerson;
    private String typeDocPerson;
    private String firstNamePerson;
    private String lastNamePerson;
    private Integer statusPerson;
    private String userCreatePerson;
    private Timestamp dateCreatePerson;
}
