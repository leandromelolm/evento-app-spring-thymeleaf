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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Participante;
import com.eventoapp.models.Telefone;
import com.eventoapp.models.Usuario;
import com.eventoapp.repository.EventoRepository;
import com.eventoapp.repository.ParticipanteRepository;
import com.eventoapp.repository.TelefoneRepository;
import com.eventoapp.repository.UsuarioRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ParticipanteRepository pr;
	
	@Autowired
	private TelefoneRepository tRepository;
	
	@Autowired
	private UsuarioRepository ur;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/teste", method=RequestMethod.GET)
	public String test() {
		return "/testPage";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {	
		List<String> msg = new ArrayList<String>();		
		if(result.hasErrors()){
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
				System.out.println("ErrorMessage__ "+ objectError.getDefaultMessage());
			}		
			attributes.addFlashAttribute("mensagem", "Verifique os campos!  "+ msg.toString().substring(1, msg.toString().length()-1));			
			return "redirect:/cadastrarEvento";
		}		
		Usuario usuario = ur.findByEmail(evento.getEmailResponsavelEvento());
		evento.setUsuario(usuario);
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado!"+ " Codigo do Evento: " +evento.getCodigo());
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value="/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("listaEventos");
//		Iterable<Evento> eventos = er.findAll();
		List<Evento> eventos = er.findAllEventos("listaEventos");
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@GetMapping("/user/meus-eventos")
	public ModelAndView meusEventos() {	
		ModelAndView mv = new ModelAndView("/user/meus-eventos");
		List<Evento> eventos = er.findAllEventos("listaEventos");
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
	
	@RequestMapping(value="/evento/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		Evento evento = er.findByCodigo(codigo);
		mv.addObject("evento", evento);
		
//		Iterable<Participante> participantes = pr.findByEvento(evento);
		List<Participante> participantes = pr.findByEventoParticipantes2(codigo);
		mv.addObject("participantes", participantes); // enviado lista de participantes para view
		return mv;
	}
	
	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/user/meus-eventos/delete2")
	public String deletarMeuEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);		
		er.delete(evento);
		return "redirect:/user/meus-eventos";
	}
	
	@PostMapping("/user/meus-eventos/delete")
	public String alterarPapelUsuario(@ModelAttribute("evento")Evento evento, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("ErrorMessage__ "+ bindingResult.hasErrors());
			return "redirect:/user/meus-eventos";
		}		
		System.out.println(evento.getEmailResponsavelEvento());
		String emailCapturadoDoForm = evento.getEmailResponsavelEvento();
		Evento e = er.findByCodigo(evento.getCodigo());

		if(!e.getEmailResponsavelEvento().equals(emailCapturadoDoForm)) {
			return "error";
		}
		er.delete(evento);
		return "redirect:/user/meus-eventos";
	}
	
	
	/* SALVAR PARTICIPANTE  */
	@RequestMapping(value="/evento/{codigo}", method = RequestMethod.POST)
	public String salvarParticipante(@PathVariable("codigo") long codigo, @Valid Participante participante, BindingResult result, @Valid String telefone, RedirectAttributes attributes){		
		List<String> msg = new ArrayList<String>();		
		
		if(result.hasErrors()){
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
				System.out.println("ErrorMessage__ "+ objectError.getDefaultMessage());
			}		
			attributes.addFlashAttribute("mensagem", "Verifique os campos!  "+ msg.toString().substring(1, msg.toString().length()-1));			
			return "redirect:/evento/{codigo}";
		}
		
		Evento evento = er.findByCodigo(codigo);
		
		participante.setEvento(evento);		
		LocalDateTime localDateTime = LocalDateTime.now();          
        participante.setDataCadastro(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())); // Conversão LocalDateTime para Date
		pr.save(participante);
		
		evento.setQuantParticip(evento.getQuantParticip()+1);
		er.save(evento);
		
		Telefone tel = new Telefone();;
		tel.setDdd(telefone.substring(1, 3));
		tel.setNumero(telefone.substring(5));
		tel.setParticipante(participante);
		tRepository.save(tel);		
		attributes.addFlashAttribute("mensagem", "Participante adicionado!");	
		return "redirect:/evento/{codigo}";
	}
	
	@RequestMapping("/deletarParticipante")
	public String deletarParticipante(long codigo) {		
		Participante participante = pr.findByIdParticipante(codigo);
		
		Evento evento = participante.getEvento();
		evento.setQuantParticip(evento.getQuantParticip()-1);
		er.save(evento);
		
		pr.delete(participante);

		return "redirect:/evento/"+ Long.toString(evento.getCodigo());
	}
	
	@RequestMapping("/deletarParticipantePageParticipantes")
	public String deletarParticipantePageParticipantes(long codigo) {
		Participante participante = pr.findByIdParticipante(codigo);
		pr.delete(participante);
		return "redirect:/participantes";
	}
	
}
