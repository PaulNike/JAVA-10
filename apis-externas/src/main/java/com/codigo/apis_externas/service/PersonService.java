package com.codigo.apis_externas.service;

import com.codigo.apis_externas.aggregates.response.ReniecResponse;
import com.codigo.apis_externas.aggregates.response.ResponseBase;
import com.codigo.apis_externas.entity.PersonEntity;

public interface PersonService {

    ResponseBase<ReniecResponse> findByDni(String dni);
    ResponseBase<PersonEntity> registerPerson(String dni);

}
