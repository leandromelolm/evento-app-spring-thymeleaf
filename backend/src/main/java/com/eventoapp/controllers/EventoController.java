package com.eventoapp.controllers;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
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
import com.eventoapp.models.ParticipanteDTO;
import com.eventoapp.models.Telefone;
import com.eventoapp.models.Usuario;
import com.eventoapp.models.enums.StatusEvento;
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
		return "user/cadastrar-evento";
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
		List<Evento> eventos = er.findAllEventos();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@GetMapping("/user/meus-eventos")
	public ModelAndView meusEventos() {	
		ModelAndView mv = new ModelAndView("user/meus-eventos");
		List<Evento> eventos = er.findAllEventos();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@GetMapping("/participantes")
	public ModelAndView listarTodosParticipantes() {
		ModelAndView mv = new ModelAndView("listaParticipantes");
//		Iterable<Participante> participantes = pr.findAll(); 
//		List<Participante> participantes = pr.findAllParticipantes(Sort.by("nomeParticipante"));
//		List<Participante> participantes = pr.findAllParticipantes(Sort.by("idParticipante").descending());
		List<Participante> participantes = pr.findAll();
		
		List<ParticipanteDTO> listParticipantesDto = participantes.stream()
				.map(obj -> new ParticipanteDTO(obj)).collect(Collectors.toList());			
		
		mv.addObject("participantes", listParticipantesDto);
		return mv;
	}
	
	@RequestMapping(value="/evento/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		ModelAndView mv = new ModelAndView("detalhesEvento");
		Evento evento = er.findByCodigo(codigo);
		mv.addObject("evento", evento);
		
//		Iterable<Participante> participantes = pr.findByEvento(evento);
		List<Participante> participantes = pr.findByEventoParticipantes2(codigo);
		participantes.sort((p1, p2) -> {
			return p2.getIdParticipante().compareTo(p1.getIdParticipante());
	    });
		
		List<ParticipanteDTO> listParticipantesDto = participantes.stream()
				.map(obj -> new ParticipanteDTO(obj)).collect(Collectors.toList());		
		
		mv.addObject("participantes", listParticipantesDto); // enviado lista de participantes para view
		return mv;
	}
	
	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/user/meus-eventos/deletar")
	public String deletarMeuEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);		
		er.delete(evento);
		return "redirect:/user/meus-eventos";
	}
	
	@PostMapping("/user/meus-eventos/delete")
	public String excluirMeuEvento(@ModelAttribute("evento")Evento evento, BindingResult bindingResult, Model model) {
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
	
	@GetMapping("/user/meus-eventos/alterar-evento/{id}")
	public String alterarEvento(@PathVariable("id") long id, Model model) {
		Optional<Evento> eventoOpt = er.findById(id);
		if(!eventoOpt.isPresent()) {
			throw new IllegalArgumentException("Evento inválido.");
		}
		Evento evento = new Evento(eventoOpt.get());
		
		model.addAttribute("eventoForm", evento);
		
		List<String> descricaoStatus = new ArrayList<String>();
		for(StatusEvento s : StatusEvento.values()){
			//System.out.println(s.getDescricao());
			descricaoStatus.add(s.getDescricao());
		}		
		model.addAttribute("statusEvento", descricaoStatus);
		return "user/editar-evento";
	}
	
	@PostMapping("/user/meus-eventos/alterar-evento")
	public String alterarEventoPost( @ModelAttribute("eventoForm") Evento evento, @Param("s") String status, 
			BindingResult result, RedirectAttributes attributes) throws Exception {	
		List<String> msg = new ArrayList<String>();		
		if(result.hasErrors()){
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}						
			return "redirect:/user/meus-eventos";
		}		
		Evento eventoAlterado = er.findById(evento.getCodigo()).orElseThrow(() -> new InvalidParameterException("Evento Inválido!"));
		if (!evento.getEmailResponsavelEvento().equals(eventoAlterado.getEmailResponsavelEvento()) ) {
			throw new Exception("Usuário não pode alterar evento de outro usuário");
		}		
		eventoAlterado.setNome(evento.getNome());
		eventoAlterado.setLocal(evento.getLocal());
		eventoAlterado.setData(evento.getData());
		eventoAlterado.setHorario(evento.getHorario());		
		eventoAlterado.addStatusEvento(StatusEvento.valueOf(status.toUpperCase()));
		//eventoAlterado.addStatusEvento(StatusEvento.ENCERRADO);
		er.save(eventoAlterado);
		attributes.addFlashAttribute("mensagem", "Evento Alterado!"+ " Status atual: " +status.toUpperCase());
		return "redirect:/user/meus-eventos/alterar-evento/"+ eventoAlterado.getCodigo();
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
