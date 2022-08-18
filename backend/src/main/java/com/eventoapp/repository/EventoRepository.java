package com.eventoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, Long> {
	Evento findByCodigo(long codigo);
	
	@Query(value = "SELECT p FROM Evento p ORDER BY status ASC, data ASC")		
	List<Evento> findAllEventos(); //home
	
	@Query(value = "SELECT e FROM Evento e WHERE e.status = :status ORDER BY data ASC")		
	List<Evento> findAllEventosStatus(Integer status); //index
	
	@Transactional(readOnly = true)
	@Query(value = "SELECT e FROM Evento e WHERE e.emailResponsavelEvento = :email")
	List<Evento> findEventosByEmail(@Param("email")String email);
}

/*

SELECT u.email, e.nome, e.codigo
FROM evento e
INNER JOIN usuario u 
ON e.email_responsavel_evento  = u.email 

*/