package com.eventoapp.controllers;

import java.util.List;

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
	
	@RequestMapping("/info")
	public String info() {
		return "/info";
	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		List<Evento> eventos = er.findAllEventos("listaEventos");
		
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		List<Evento> eventos = er.findAllEventos("listaEventos");
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	// SignIn form
	@RequestMapping("/login.html")
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
