package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Usuario;

@Repository
public interface UserRepository extends CrudRepository<Usuario, String> {
	Usuario findByEmail(String email);
}
