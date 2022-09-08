package com.eventoapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.eventoapp.models.Evento;
import com.eventoapp.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Page<Evento> searchEventoPaginated2(String nome, Integer page, Integer linesPerPage, String orderBy,
            String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return eventoRepository.searchEventoPaginated(nome.toLowerCase(), pageRequest);
    }

    public Page<Evento> searchEventoPaginated(String nome, Integer page, Integer linesPerPage, String orderBy,
            String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return eventoRepository.findByNomeContainingIgnoreCase(nome, pageRequest);
    }

    public Page<Evento> searchEventoAndStatusPaginated(String nome, Integer status, Integer page, Integer linesPerPage,
            String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return eventoRepository.findByStatusAndNomeContainingIgnoreCase(status, nome, pageRequest);
    }

    public Page<Evento> getAllEventos(int page, int size ){
        Sort sort = Sort.by("status").ascending().and(Sort.by("data").ascending());
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.eventoRepository.findAll(pageable);
    }

    public Page<Evento> getAllEventosByStatus(Integer status, String nome,  int page, int size){
        Sort sort = Sort.by("data").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.eventoRepository.findByStatusAndNomeContainingIgnoreCase(status, nome, pageable);
    }

    public Integer retornaStatusEventoInt(String eventoStatus) {
        Integer intStatus = null;
        switch (eventoStatus.toLowerCase()) {
            case "aberto":
                intStatus = 1;
                break;
            case "pausado":
                intStatus = 2;
                break;
            case "encerrado":
                intStatus = 3;
                break;
            case "finalizado":
                intStatus = 4;
                break;
        }
        return intStatus;
    }

}
