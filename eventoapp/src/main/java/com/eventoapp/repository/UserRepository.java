package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Usuario;

public interface UserRepository extends CrudRepository<Usuario, String> {
	Usuario findByEmail(String email);
}
