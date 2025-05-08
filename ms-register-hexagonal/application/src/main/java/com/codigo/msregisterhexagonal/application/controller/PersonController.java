package com.codigo.msregisterhexagonal.application.controller;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;
import com.codigo.msregisterhexagonal.domain.ports.in.PersonServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hexagonal/")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceIn serviceIn;

    @PostMapping("/save")
    public ResponseEntity<PersonDTO> createPerson(@RequestParam("dni") String dni){
        return ResponseEntity.ok(serviceIn.createPersonIn(dni));
    }
}
