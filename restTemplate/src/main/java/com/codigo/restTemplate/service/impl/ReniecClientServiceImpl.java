package com.codigo.restTemplate.service.impl;

import com.codigo.restTemplate.aggregates.constants.Constants;
import com.codigo.restTemplate.aggregates.response.ReniecResponse;
import com.codigo.restTemplate.service.ReniecClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReniecClientServiceImpl implements ReniecClientService {

    private final RestTemplate restTemplate;

    @Value("${token.api}")
    private String tokenApi;
    @Override
    public Optional<ReniecResponse> getInfoReniec(String dni) {

        String urlComplete = Constants.BASE_URL + "v2/reniec/dni?numero="+dni;

        try{
            ResponseEntity<ReniecResponse> response = restTemplate.exchange(
                    urlComplete,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    ReniecResponse.class
            );

            if(response.getStatusCode() == HttpStatus.OK){
                return Optional.of(response.getBody());
            }else {
                log.warn("Respuesta Inesperada del servicio de RENIEC: {}", response.getStatusCode());
            }
        } catch (Exception e){
            log.error("Respuesta Inesperada del servicio de RENIEC: {}", dni, e);
        }
        return Optional.empty();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization",Constants.BEARER+tokenApi);
        return header;
    }
}
