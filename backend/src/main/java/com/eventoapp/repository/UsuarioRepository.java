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
	
	@Transactional(readOnly = true)
	Usuario findByEmail(String email);
	
	@Transactional(readOnly = true)
	Usuario findById(Long id);
	
	@Transactional
	@Modifying
	@Query(
		value =
			"INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (:idusuario, :idrole)",
			nativeQuery = true)
	void InsertRole(@Param("idusuario") Long idUsuario, @Param("idrole")long idRole);
	
	@Transactional
	@Modifying
	@Query(
		value =
			"DELETE FROM usuarios_eventos WHERE usuario_email LIKE :email",
			nativeQuery = true)
	void deletarUsuarioEvento(@Param("email") String email);
	
	@Transactional
	@Modifying
	@Query(
		value =
			"DELETE FROM usuarios_roles WHERE usuario_id = :id",
			nativeQuery = true)
	void deletarUsuarioRole(@Param("id") long id);
		
	@Transactional
	@Modifying
	@Query(
		value =
			"DELETE FROM usuario WHERE id = :id",
			nativeQuery = true)
	void deletarUsuario(@Param("id") long id);
	
//	@Transactional
//	@Modifying
//	@Query(
//		value =
//			"DELETE FROM evento WHERE email_responsavel_evento LIKE :email",
//			nativeQuery = true)
//	void deletarUsuarioTodosEventos(@Param("email") String email);	
	
}