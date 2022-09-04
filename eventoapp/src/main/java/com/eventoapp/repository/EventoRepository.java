package com.eventoapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eventoapp.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
	Evento findByCodigo(long codigo);
	
	@Query(value = "SELECT p FROM Evento p ORDER BY status ASC, data ASC")		
	List<Evento> findAllEventos(); //home
	
	@Query(value = "SELECT e FROM Evento e WHERE e.status = :status ORDER BY data ASC")		
	List<Evento> findAllEventosStatus(Integer status); //index
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT e FROM Evento e WHERE e.emailResponsavelEvento = :email")
	List<Evento> findEventosByEmail(@Param("email")String email);

	@Transactional(readOnly = true)
	@Query ("SELECT e FROM Evento e WHERE e.nome LIKE %:nomeEvento%")
	Page<Evento> searchEventoPaginated2(@Param ("nomeEvento") String nomeEvento, Pageable pageRequest);

	@Transactional(readOnly = true)
	@Query ("SELECT e FROM Evento e WHERE lower(e.nome) LIKE %:nomeEvento%")
	Page<Evento> searchEventoPaginated(@Param ("nomeEvento") String nomeEvento, Pageable pageRequest);

	@Transactional(readOnly = true)
	Page<Evento> findByNomeContainingIgnoreCase(String name, Pageable pageRequest);
}


// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/ 
// Example 57. Query creation from method names