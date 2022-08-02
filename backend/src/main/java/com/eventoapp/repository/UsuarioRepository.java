package com.eventoapp.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventoapp.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
	Usuario findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query(
		value =
			"Insert Into usuarios_roles (usuario_id, role_id) Values (:idusuario, :idrole)",
			nativeQuery = true)
	void InsertRole(@Param("idusuario") Long idUsuario, @Param("idrole")long idRole);
}