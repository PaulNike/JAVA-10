package com.codigo.msregisterhexagonal.infraestructure.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.util.UUID;

@Document(collection = "persons")
@Data
public class PersonEntityDoc {

    private Long id;
    @Field(name = "num_doc")
    private String numDoc;
    @Field(name = "type_doc")
    private String typeDoc;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "mother_last_name")
    private String motherLastName;
    @Field(name = "status")
    private Integer status;
    @Field(name = "user_create")
    private String userCreate;
    @Field(name = "date_create")
    private Timestamp dateCreate;

    public PersonEntityDoc(){
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
