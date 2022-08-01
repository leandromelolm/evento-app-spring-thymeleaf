package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
	Usuario findByEmail(String email);
}
