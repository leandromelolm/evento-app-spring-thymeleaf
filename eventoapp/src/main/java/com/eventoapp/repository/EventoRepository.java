package com.eventoapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventoapp.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
	
	@Transactional(readOnly = true)
	Evento findByCodigo(long codigo);
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT p FROM Evento p ORDER BY status ASC, data ASC")		
	List<Evento> findAllEventos();
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT e FROM Evento e WHERE e.status = :status ORDER BY data ASC")		
	List<Evento> findAllEventosStatus(@Param("status")Integer status);
	
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

	@Transactional(readOnly = true)
	Page<Evento> findByStatusAndNomeContainingIgnoreCase(Integer status, String nome, Pageable pageRequest);

	@Transactional(readOnly = true)
	Page<Evento> findAll(Pageable pageable);
}


// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/ 
// 5.1.3. Query Methods
// Example 57. Query creation from method names