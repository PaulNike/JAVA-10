package com.codigo.restTemplate.service;

import com.codigo.restTemplate.aggregates.response.ReniecResponse;

import java.util.Optional;

public interface ReniecClientService {
    Optional<ReniecResponse> getInfoReniec(String dni);
}
