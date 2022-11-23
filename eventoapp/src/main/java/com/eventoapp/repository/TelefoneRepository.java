package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Telefone;

@Repository
public interface TelefoneRepository extends CrudRepository<Telefone, String> {

}
