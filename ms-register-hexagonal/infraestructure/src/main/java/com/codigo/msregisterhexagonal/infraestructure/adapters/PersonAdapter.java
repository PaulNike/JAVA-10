package com.codigo.msregisterhexagonal.infraestructure.adapters;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;
import com.codigo.msregisterhexagonal.domain.ports.out.PersonServiceOut;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntityDoc;
import com.codigo.msregisterhexagonal.infraestructure.repository.PersonRepository;
import com.codigo.msregisterhexagonal.infraestructure.repository.PersonRepositoryDoc;
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
    private final PersonRepositoryDoc repositoryDoc;
    private final ModelMapper personDocMapper;


    @Value("${token.api}")
    private String token;

    public PersonAdapter(ReniecClient reniecClient,
                         @Qualifier("defaultMapper") ModelMapper personMapper,
                         @Qualifier("reniecMapper") ModelMapper reniecMapper,
                         PersonRepository personRepository,
                         PersonRepositoryDoc repositoryDoc,
                         @Qualifier("reniecMapperDoc") ModelMapper personDocMapper) {
        this.reniecClient = reniecClient;
        this.personMapper = personMapper;
        this.reniecMapper = reniecMapper;
        this.personRepository = personRepository;
        this.repositoryDoc = repositoryDoc;
        this.personDocMapper = personDocMapper;
    }

    //TAREITA
    @Override
    public PersonDTO createPersonOut(String dni) {
        //return mapToPersonDTO(personRepository.save(getEntityForSave(dni)));
        return mapToPersonDTO(repositoryDoc.save(getEntityForSave(dni)));
    }

    private PersonEntityDoc getEntityForSave(String dni){
        log.info("{} - {} INICIO, getEntityForSave to: ", dni);
        ResponseReniec responseReniec = executeReniec(dni);

        if (responseReniec ==  null ||
        responseReniec.getNumeroDocumento() == null){
            throw new RuntimeException("Respuesta invalida de RENIEC: "+ dni);
        }
        PersonEntityDoc personEntityDoc = mapReniecToDocument(responseReniec);
        //PersonEntity person = mapReniecToPersonEntity(responseReniec);
        personEntityDoc.setStatus(1);
        personEntityDoc.setUserCreate("PRODRIGUEZ");
        personEntityDoc.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return personEntityDoc;
    }

    private ResponseReniec executeReniec(String dni){
        log.info("Consultando los datos a RENIEC para el DNI: {}", dni);
        String header = "Bearer "+token;
        return Optional.ofNullable(reniecClient.getInfoReniec(dni,header))
                .orElseThrow(() -> new RuntimeException("Error al consutla con Reniec"));

    }
    //Ajustamos el PARAM
    private PersonDTO mapToPersonDTO(PersonEntityDoc personEntityDoc){
        return personMapper.map(personEntityDoc,PersonDTO.class);
    }

    private PersonEntity mapReniecToPersonEntity(ResponseReniec responseReniec){
        return reniecMapper.map(responseReniec, PersonEntity.class);
    }

    private PersonEntityDoc mapReniecToDocument(ResponseReniec responseReniec){
        return personDocMapper.map(responseReniec, PersonEntityDoc.class);
    }
}
