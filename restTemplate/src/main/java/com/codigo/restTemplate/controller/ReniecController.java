package com.codigo.restTemplate.controller;

import com.codigo.restTemplate.aggregates.response.ReniecResponse;
import com.codigo.restTemplate.service.ReniecClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reniec/v1")
public class ReniecController {
    private final ReniecClientService reniecClient;

    @GetMapping("/{dni}")
    public ResponseEntity<ReniecResponse> findByDni(@PathVariable String dni){
        Optional<ReniecResponse> response = reniecClient.getInfoReniec(dni);
        if (response.isPresent()){
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ReniecResponse(), HttpStatus.CONFLICT);

        }
    }
    @GetMapping("/v2/{dni}")
    public ResponseEntity<ReniecResponse> findByDni2(@PathVariable String dni){
       return reniecClient.getInfoReniec(dni)
               .map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity
                       .status(409)
                       .body(new ReniecResponse()));
    }
}
