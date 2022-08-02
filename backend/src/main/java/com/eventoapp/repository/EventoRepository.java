package com.eventoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> {
	Evento findByCodigo(long codigo);
	
	@Query(value = "SELECT p FROM Evento p ORDER BY codigo DESC")		
	List<Evento> findAllEventos(String evento);
}

/*

SELECT u.email, e.nome, e.codigo
FROM evento e
INNER JOIN usuario u 
ON e.email_responsavel_evento  = u.email 

*/