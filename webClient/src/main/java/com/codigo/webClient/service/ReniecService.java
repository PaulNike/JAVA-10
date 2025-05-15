package com.codigo.webClient.service;

import com.codigo.webClient.response.ReniecResponse;
import reactor.core.publisher.Mono;

public interface ReniecService {
    Mono<ReniecResponse> getInfoReniec(String dni);
}
