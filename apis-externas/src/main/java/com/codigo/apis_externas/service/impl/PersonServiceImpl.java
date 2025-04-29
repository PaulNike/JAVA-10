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
        reniecResponse = execution(dni);

        //Preparando respuesta a entregar
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(2004);
        responseBase.setMessage("Todo OK!!");
        responseBase.setEntity(Optional.of(reniecResponse));
        return responseBase;
    }

    private ReniecResponse execution(String dni) {
        log.info("Ejecutando consulta a RENIEC API para el DNI: {}", dni);
        String tokenOk = "Bearer "+token;
        return clientReniec.getPerson(dni,tokenOk);
    }

    @Override
    public ResponseBase<PersonEntity> registerPerson(String dni) {
        log.info("Registrando Persona con DNI: {}", dni);
        ResponseBase responseBase = new ResponseBase();

        //Ejecutar la consulta al cliente externo (Reniec)
        ReniecResponse reniecResponse = execution(dni);

        if(reniecResponse == null){
            responseBase.setCode(4000);
            responseBase.setMessage("Ocurrio un Error no existe respuesta de Reniec!!");
            responseBase.setEntity(Optional.empty());
            return responseBase;
        }

        //Registrar a la persona
        PersonEntity personEntity = new PersonEntity();
        personEntity.setNames(reniecResponse.getNombres());
        personEntity.setFullName(reniecResponse.getNombreCompleto());
        personEntity.setLastName(reniecResponse.getApellidoPaterno());
        personEntity.setMotherLastName(reniecResponse.getApellidoMaterno());
        personEntity.setTypeDocument(reniecResponse.getTipoDocumento());
        personEntity.setNumberDocument(reniecResponse.getNumeroDocumento());
        personEntity.setCheckDigit(reniecResponse.getDigitoVerificador());
        personEntity.setStatus(Constants.STATUS_ACTIVE);
        personEntity.setUserCreated(Constants.USER_ADMIN);
        personEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        //Guardando a la persona
        PersonEntity personSave = personRepository.save(personEntity);

        responseBase.setCode(2001);
        responseBase.setMessage("Todo OK!!");
        responseBase.setEntity(Optional.of(personSave));

        return responseBase;
    }

    @Override
    public ResponseBase<List<PersonEntity>> findPersonActive() {
        ResponseBase responseBase = new ResponseBase();
        List<PersonEntity> listPersonActive =
                personRepository.findAllByStatus(Constants.STATUS_ACTIVE);

        responseBase.setCode(2001);
        responseBase.setMessage("Todo OK!!");
        responseBase.setEntity(Optional.of(listPersonActive));
        return responseBase;
    }
}
