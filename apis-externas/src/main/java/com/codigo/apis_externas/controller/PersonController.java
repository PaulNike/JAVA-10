package com.codigo.apis_externas.controller;

import com.codigo.apis_externas.aggregates.response.ReniecResponse;
import com.codigo.apis_externas.aggregates.response.ResponseBase;
import com.codigo.apis_externas.entity.PersonEntity;
import com.codigo.apis_externas.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/find/{dni}")
    public ResponseEntity<ResponseBase<ReniecResponse>> findPerson(@PathVariable String dni){
        return new ResponseEntity<>(personService.findByDni(dni), HttpStatus.OK);
    }

    @PostMapping("/save/{dni}")
    public ResponseEntity<ResponseBase<PersonEntity>> savePerson(@PathVariable String dni){
        return new ResponseEntity<>(personService.registerPerson(dni), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<ResponseBase<List<PersonEntity>>> findAllActive(){
        return new ResponseEntity<>(personService.findPersonActive(), HttpStatus.OK);
    }
}
