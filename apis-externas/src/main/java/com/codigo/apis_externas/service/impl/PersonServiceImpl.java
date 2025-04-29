package com.codigo.apis_externas.service.impl;

import com.codigo.apis_externas.aggregates.constants.Constants;
import com.codigo.apis_externas.aggregates.response.ReniecResponse;
import com.codigo.apis_externas.aggregates.response.ResponseBase;
import com.codigo.apis_externas.client.ClientReniec;
import com.codigo.apis_externas.entity.PersonEntity;
import com.codigo.apis_externas.repository.PersonRepository;
import com.codigo.apis_externas.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final ClientReniec clientReniec;
    private final PersonRepository personRepository;

    @Value("${value.token}")
    private String token;

    @Override
    public ResponseBase<ReniecResponse> findByDni(String dni) {
        ReniecResponse reniecResponse;
        log.info("Buscando Información para el DNI: {}", dni);
        //Ejecutando la consulta al api de reniec
        reniecResponse = executionReniec(dni);

        //Preparando respuesta a entregar
        /*ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(2004);
        responseBase.setMessage("Todo OK!!");
        responseBase.setEntity(Optional.of(reniecResponse));
        return responseBase;*/
        return buildResponse(2004,"Todo OK!!",Optional.of(reniecResponse));

    }

    private ReniecResponse executionReniec(String dni) {
        log.info("Ejecutando consulta a RENIEC API para el DNI: {}", dni);
        String tokenOk = "Bearer "+token;
        return clientReniec.getPerson(dni,tokenOk);
    }

    @Override
    public ResponseBase<PersonEntity> registerPerson(String dni) {
        log.info("Registrando Persona con DNI: {}", dni);
        //ResponseBase responseBase = new ResponseBase();

        //Ejecutar la consulta al cliente externo (Reniec)
        ReniecResponse reniecResponse = executionReniec(dni);

        if(reniecResponse == null){
            /*responseBase.setCode(4000);
            responseBase.setMessage("Ocurrio un Error no existe respuesta de Reniec!!");
            responseBase.setEntity(Optional.empty());
            return responseBase;*/
            return buildResponse(4000,
                    "Ocurrio un Error no existe respuesta de Reniec!!",
                    Optional.empty());
        }

        //Preparando la entidad de Persona
        PersonEntity personEntity = buildPersonEntity(reniecResponse);
        //Guardando a la persona
        PersonEntity personSave = personRepository.save(personEntity);

        /*responseBase.setCode(2001);
        responseBase.setMessage("Todo OK!!");
        responseBase.setEntity(Optional.of(personSave));

        return responseBase;*/
        return buildResponse(2001,"Todo OK!!",Optional.of(personSave));
    }

    @Override
    public ResponseBase<List<PersonEntity>> findPersonActive() {
        //ResponseBase responseBase = new ResponseBase();
        List<PersonEntity> listPersonActive =
                personRepository.findAllByStatus(Constants.STATUS_ACTIVE);

        /*responseBase.setCode(2001);
        responseBase.setMessage("Todo OK!!");
        responseBase.setEntity(Optional.of(listPersonActive));
        return responseBase;*/
        return buildResponse(2001,"Todo OK!!", Optional.of(listPersonActive));
    }

    private <T> ResponseBase<T> buildResponse(
            int code, String message, Optional<T> optional){
        ResponseBase<T> responseBase = new ResponseBase<>();
        responseBase.setCode(code);
        responseBase.setMessage(message);
        responseBase.setEntity(optional);
        return  responseBase;
    }
    private PersonEntity buildPersonEntity(ReniecResponse reniecResponse){
        return PersonEntity.builder()
                .names(reniecResponse.getNombres())
                .fullName(reniecResponse.getNombreCompleto())
                .lastName(reniecResponse.getApellidoPaterno())
                .motherLastName(reniecResponse.getApellidoMaterno())
                .typeDocument(reniecResponse.getTipoDocumento())
                .numberDocument(reniecResponse.getNumeroDocumento())
                .checkDigit(reniecResponse.getDigitoVerificador())
                .status(Constants.STATUS_ACTIVE)
                .userCreated(Constants.USER_ADMIN)
                .dateCreated(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
