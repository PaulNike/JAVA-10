package com.codigo.msregisterhexagonal.infraestructure.adapters;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;
import com.codigo.msregisterhexagonal.domain.ports.out.PersonServiceOut;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import com.codigo.msregisterhexagonal.infraestructure.repository.PersonRepository;
import com.codigo.msregisterhexagonal.infraestructure.response.ResponseReniec;
import com.codigo.msregisterhexagonal.infraestructure.rest.ReniecClient;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Log4j2
public class PersonAdapter implements PersonServiceOut {
    private final ReniecClient reniecClient;
    private final ModelMapper personMapper;
    private final ModelMapper reniecMapper;
    private final PersonRepository personRepository;

    @Value("${token.api}")
    private String token;

    public PersonAdapter(ReniecClient reniecClient,
                         @Qualifier("defaultMapper") ModelMapper personMapper,
                         @Qualifier("reniecMapper") ModelMapper reniecMapper,
                         PersonRepository personRepository) {
        this.reniecClient = reniecClient;
        this.personMapper = personMapper;
        this.reniecMapper = reniecMapper;
        this.personRepository = personRepository;
    }

    //TAREITA
    @Override
    public PersonDTO createPersonOut(String dni) {
        return mapToPersonDTO(personRepository.save(getEntityForSave(dni)));
    }

    private PersonEntity getEntityForSave(String dni){
        log.info("{} - {} INICIO, getEntityForSave to: ", dni);
        ResponseReniec responseReniec = executeReniec(dni);

        if (responseReniec ==  null ||
        responseReniec.getNumeroDocumento() == null){
            throw new RuntimeException("Respuesta invalida de RENIEC: "+ dni);
        }
        PersonEntity person = mapReniecToPersonEntity(responseReniec);
        person.setStatus(1);
        person.setUserCreate("PRODRIGUEZ");
        person.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return person;
    }

    private ResponseReniec executeReniec(String dni){
        log.info("Consultando los datos a RENIEC para el DNI: {}", dni);
        String header = "Bearer "+token;
        return Optional.ofNullable(reniecClient.getInfoReniec(dni,header))
                .orElseThrow(() -> new RuntimeException("Error al consutla con Reniec"));

    }
    private PersonDTO mapToPersonDTO(PersonEntity personEntity){
        return personMapper.map(personEntity,PersonDTO.class);
    }

    private PersonEntity mapReniecToPersonEntity(ResponseReniec responseReniec){
        return reniecMapper.map(responseReniec, PersonEntity.class);
    }
}
