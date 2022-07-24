package com.eventoapp.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Participante;
import com.eventoapp.models.Telefone;
import com.eventoapp.repository.EventoRepository;
import com.eventoapp.repository.ParticipanteRepository;
import com.eventoapp.repository.TelefoneRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ParticipanteRepository pr;
	
	@Autowired
	private TelefoneRepository tRepository;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(Evento evento) {		
		er.save(evento);		
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value="/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("listaEventos");
		Iterable<Evento> eventos = er.findAll(); 
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		Evento evento = er.findByCodigo(codigo);
		mv.addObject("evento", evento);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, String telefone,  Participante participante){
		Evento evento = er.findByCodigo(codigo);
		
		participante.setEvento(evento);		
		LocalDateTime localDateTime = LocalDateTime.now();          
        participante.setDataCadastro(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())); // Conversão LocalDateTime para Date
		pr.save(participante);	
		
		Telefone tel = new Telefone();;
		tel.setDdd(telefone.substring(1, 3));
		tel.setNumero(telefone.substring(5));
		tel.setParticipante(participante);
		tRepository.save(tel);		
			
		return "redirect:/{codigo}";
	}
	
}
