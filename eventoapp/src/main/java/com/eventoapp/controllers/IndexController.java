package com.eventoapp.controllers;

import java.util.List;

import com.eventoapp.models.Usuario;
import com.eventoapp.repository.UsuarioRepository;
import com.eventoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Evento;
import com.eventoapp.repository.EventoRepository;

@Controller
public class IndexController {
	
	@Autowired
	private EventoRepository er;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping("/infodesenvolvimento")
	public String info() {
		return "infoDev";
	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		List<Evento> listaEventosAberto = er.findAllEventosStatus(1); // 1 = status Aberto		
		mv.addObject("eventos", listaEventosAberto);
		return mv;
	}
	
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		List<Evento> eventos = er.findAllEventos();
		if(UserService.authenticated() != null){
			Usuario usuarioLogado = usuarioRepository.findByEmail(UserService.authenticated().getUsername());
			mv.addObject("usuarioLogadoNome", usuarioLogado.getNome());
		}
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	// SignIn form
	@RequestMapping("/login")
	public String login() {
		return "login.html";
	}

	// Login form with error
	@RequestMapping("/login-error.html")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}
	
	
}
