package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Participante;

public interface ParticipanteRepository extends CrudRepository<Participante, String> {
	Iterable<Participante> findByEvento(Evento evento);

}
