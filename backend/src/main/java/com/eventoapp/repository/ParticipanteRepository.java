package com.eventoapp.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Participante;

public interface ParticipanteRepository extends CrudRepository<Participante, String> {
	
	Iterable<Participante> findByEvento(Evento evento);
	
	Participante findByIdParticipante(long idParticipante);
	
	@Query(value = "SELECT p FROM Participante p")		
	List<Participante> findAllParticipantes(Sort sort);

}
