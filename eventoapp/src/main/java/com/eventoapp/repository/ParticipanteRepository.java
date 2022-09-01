package com.eventoapp.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Participante;

@Repository
public interface ParticipanteRepository extends CrudRepository<Participante, String> {
	
	Iterable<Participante> findByEvento(Evento evento);
	
	@Transactional(readOnly=true)
	@Query(value= "SELECT * FROM participante p WHERE p.evento_codigo = :codigo", nativeQuery = true)
	List<Participante> findByEventoParticipantes(@Param("codigo")long codigo);
	
	@Transactional(readOnly=true)
	@Query(value= "SELECT p FROM Participante p WHERE p.evento.codigo = :codigo")
	List<Participante> findByEventoParticipantes2(@Param("codigo")long codigo);
	
	Participante findByIdParticipante(long idParticipante);
	
	@Query(value = "SELECT p FROM Participante p")		
	List<Participante> findAllParticipantes(Sort sort);
	
	@Transactional(readOnly=true)	
	List<Participante> findAll();
}
