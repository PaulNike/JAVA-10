package com.codigo.apis_externas.repository;

import com.codigo.apis_externas.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByStatus(String status);
    List<PersonEntity> findAllByStatus(String status);
}
