package com.eventoapp.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@RequestMapping(value="/teste", method=RequestMethod.GET)
	public String test() {
		return "/testPage";
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
	
	@RequestMapping(value="/participantes")
	public ModelAndView listarTodosParticipantes() {
		ModelAndView mv = new ModelAndView("listaParticipantes");
//		Iterable<Participante> participantes = pr.findAll(); 
		List<Participante> participantes = pr.findAllParticipantes(Sort.by("nomeParticipante"));
		mv.addObject("participantes", participantes);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		Evento evento = er.findByCodigo(codigo);
		mv.addObject("evento", evento);
		
		Iterable<Participante> participantes = pr.findByEvento(evento);
		mv.addObject("participantes", participantes); // enviado lista de participantes para view
		return mv;
	}
	
	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping(value="/{codigo}", method = RequestMethod.POST)
	public String salvarParticipante(@PathVariable("codigo") long codigo, @Valid Participante participante, BindingResult result, @Valid String telefone, RedirectAttributes attributes){		
		
		System.out.println("ErroQuant__ "+ result.getErrorCount());
		System.out.println("ErrorBolean__ "+ result.hasErrors());
		System.out.println("Erro__ "+ result.getAllErrors());
		
		List<String> msg = new ArrayList<String>();			
		
		if(result.hasErrors()){
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
				System.out.println("ErrorMessage__ "+ objectError.getDefaultMessage());
			}		
			attributes.addFlashAttribute("mensagem", "Verifique os campos!  "+ msg.toString().substring(1, msg.toString().length()-1));			
			return "redirect:/{codigo}";
		}
		
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
		attributes.addFlashAttribute("mensagem", "Participante adicionado!");	
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarParticipante")
	public String deletarParticipante(long codigo) {
		Participante participante = pr.findByIdParticipante(codigo);
		pr.delete(participante);
		Evento evento = participante.getEvento();		
		return "redirect:/"+ Long.toString(evento.getCodigo());
	}
	
}
