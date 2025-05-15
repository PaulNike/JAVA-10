package com.codigo.webClient.controller;

import com.codigo.webClient.response.ReniecResponse;
import com.codigo.webClient.service.ReniecService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reniec")
@RequiredArgsConstructor
@Log4j2
public class ReniecController {

    private final ReniecService reniecService;

    @GetMapping("/{dni}")
    public Mono<ReniecResponse> findByDni(@PathVariable String dni){
        return reniecService.getInfoReniec(dni)
                .doOnNext(reponse -> log.info("resultado: ", reponse))
                .doOnError(error -> log.error("Error: ", error));
    }
}
