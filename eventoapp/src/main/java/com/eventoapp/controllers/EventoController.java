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

import com.eventoapp.service.EventoService;
import com.eventoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Evento;
import com.eventoapp.models.EventoDTO;
import com.eventoapp.models.EventoListPagDTO;
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

	@Autowired
	private EventoService eventoService;
	
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

	@RequestMapping(value="/eventos-json")
	public ResponseEntity<Page<EventoListPagDTO>> listaEventosJson (
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="5") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="data") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {		
		Page<Evento> listaEvento = eventoService.searchEventoPaginated(nome, page, linesPerPage, orderBy, direction); 
		Page<EventoListPagDTO> listDto = listaEvento.map(obj -> new EventoListPagDTO(obj, "parteNomeResponsavelEscondido"));
		return ResponseEntity.ok().body(listDto);
		// [GET] http://localhost:8081/eventos-json/?nome={String}&page={page}
	}

	@RequestMapping(value="/eventos-paginado")
	public ModelAndView listaEventosWithPagination (
			@RequestParam(value="nomepesquisa", defaultValue="") String nome, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="5") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="data") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {				
		ModelAndView mv = new ModelAndView("listaEventos-paginated");
		Page<Evento> listaEvento = eventoService.searchEventoPaginated(nome, page, linesPerPage, orderBy, direction); 
		Page<EventoListPagDTO> listDto = listaEvento.map(obj -> new EventoListPagDTO(obj));
		mv.addObject("eventosPaginado", listDto);
		mv.addObject("nomepesquisa", nome);
		return mv;
	}

	@PostMapping("pesquisarevento")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {		
		Page<Evento> listaEvento = null;
		listaEvento = eventoService.searchEventoPaginated(nomepesquisa, 0, 5, "data", "ASC"); 
		Page<EventoListPagDTO> listDto = listaEvento.map(obj -> new EventoListPagDTO(obj));

		ModelAndView modelAndView = new ModelAndView("listaEventos-paginated");
		modelAndView.addObject("eventosPaginado", listDto);
		modelAndView.addObject("nomepesquisa", nomepesquisa);		
		return modelAndView;
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
//		List<Participante> participantes = pr.findAllParticipantes(Sort.by("nomeParticipante"));
//		List<Participante> participantes = pr.findAllParticipantes(Sort.by("idParticipante").descending());
		List<Participante> participantes = pr.findAll();
		
		List<ParticipanteDTO> listParticipantesDto = participantes.stream()
				.map(obj -> new ParticipanteDTO(obj,1)).collect(Collectors.toList());			
		
		mv.addObject("participantesDto", listParticipantesDto);
		return mv;
	}
	
	@RequestMapping(value="/evento/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) throws Exception {
		ModelAndView mv = new ModelAndView("detalhesEvento");
		Evento evento = er.findById(codigo).orElseThrow(() -> new InvalidParameterException("Evento não existe!"));			
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(evento.getStatus().getDescricao().equals("Pausado") && !auth.getName().equals(evento.getEmailResponsavelEvento())) {			
			throw new Exception("Evento Pausado! Acesso Restrito.");			
		}
		
		if(evento.getStatus().getDescricao().equals("Encerrado") && !auth.getName().equals(evento.getEmailResponsavelEvento())) {
			throw new IllegalArgumentException("Evento Encerrado! Acesso Restrito.");
		}
		
		if(evento.getStatus().getDescricao().equals("Finalizado")) {		
			throw new IllegalArgumentException("Evento Finalizado.");
		}
		
		if(evento.getStatus().getDescricao().equals("Cancelado")) {		
			throw new IllegalArgumentException("Evento foi Cancelado.");
		}
		
		mv.addObject("evento", evento);	
		
		List<Participante> participantes = pr.findByEventoParticipantes2(codigo);
		participantes.sort((p1, p2) -> {
			return p2.getIdParticipante().compareTo(p1.getIdParticipante());
	    });
		
		List<ParticipanteDTO> listParticipantesDto = participantes.stream()
				.map(obj -> new ParticipanteDTO(obj)).collect(Collectors.toList());		
		
		mv.addObject("participantes", listParticipantesDto); // enviando lista de participantes para view
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
	public String alterarEvento(@PathVariable("id") long id, Model model) throws Exception {
		
		Optional<Evento> eventoOpt = er.findById(id);

		Usuario usuarioLogado = ur.findByEmail(UserService.authenticated().getUsername());
		if (!usuarioLogado.getEmail().equals(eventoOpt.get().getEmailResponsavelEvento())) {
			throw new Exception("Acesso Proibido! Apenas o usuario responsável pelo evento pode realizar esse acesso.");
		}
			
		if(!eventoOpt.isPresent()) {
			throw new IllegalArgumentException("Evento inválido.");
		}
		
		EventoDTO eventoDto = new EventoDTO(eventoOpt.get());
		
		model.addAttribute("eventoForm", eventoDto);
		model.addAttribute("participantesEvento", eventoDto.getParticipantes());
		
		List<String> descricaoStatus = new ArrayList<String>();
		for(StatusEvento s : StatusEvento.values()){
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
		attributes.addFlashAttribute("mensagem", "Participante "+ participante.getNomeParticipante().substring(0, 3) +"** adicionado!");	
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
		
		Evento evento = participante.getEvento();
		evento.setQuantParticip(evento.getQuantParticip()-1);
		er.save(evento);
		
		pr.delete(participante);
		return "redirect:/participantes";
	}
	
}