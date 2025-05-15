package com.codigo.webClient.service.impl;

import com.codigo.webClient.response.ReniecResponse;
import com.codigo.webClient.service.ReniecService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReniecServiceImpl implements ReniecService {

    @Value("${api.reniec.token}")
    private String token;

    private final WebClient.Builder webClientBuilder;


    @Override
    public Mono<ReniecResponse> getInfoReniec(String dni) {
        return webClientBuilder.build()
                .get()
                .uri("https://api.apis.net.pe/v2/reniec/dni?numero={dni}", dni)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ReniecResponse.class)
                .doOnNext( reniecResponse -> log.info("Respuesta del API de Reniec: ", token));
    }
}
