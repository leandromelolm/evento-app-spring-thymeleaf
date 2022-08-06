package com.eventoapp.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Usuario;
import com.eventoapp.repository.EventoRepository;
import com.eventoapp.repository.UsuarioRepository;

@Controller
public class TesteController {
	
	@Autowired
	private EventoRepository er;
	
	@Autowired
	private UsuarioRepository ur;
		
	@RequestMapping(value="/teste", method=RequestMethod.GET)
	public String test() {
		return "/testPage";
	}
	
	// https://javabycode.com/sf/spring-boot-tutorial/spring-boot-thymeleaf-ajax-example.html
	@RequestMapping(value="/testepost", method=RequestMethod.POST)
	public ModelAndView testPost(@RequestBody String nome, Model model, Usuario u, RedirectAttributes attrib, HttpSession session) throws UnsupportedEncodingException {
		System.out.println("metodo testPost()...");
		String nomeDecode = URLDecoder.decode(nome, "UTF-8");
		System.out.println("nome: "+ nome +"  nomeDecode: "+ nomeDecode.replaceAll("=","") );
//		ModelAndView mv = new ModelAndView("redirect:/testPage");
		ModelAndView mv = new ModelAndView("testPage");
		Usuario user = ur.findByEmail(nomeDecode.replaceAll("=", ""));
		System.out.println("Usuario nome.."+user.getNome());
		System.out.println("Usuario cpf.."+user.getCpf());
		attrib.addFlashAttribute("mensagem", user.getNome());
		
		session.setAttribute("mySessionNome", user.getNome());
		session.setAttribute("mySessionCpf", user.getCpf());
		session.setAttribute("mySessionDataCadastro", user.getDataCadastro());
		session.setAttribute("mySessionTipoPerfil", user.getRoles().get(0).getNameRole());
		session.setAttribute("mySessionId", user.getId());
		
		mv.addObject("usuario", user);
		return mv;
//		return "redirect:/testPage";
	}
	
	@PostMapping("/deletarMinhaContaUsuario")
	public String deletarMinhaContaUsuario(@ModelAttribute("id")long id, 
			BindingResult bindingResult, RedirectAttributes attributes) {
		Usuario usuario = ur.findById(id);
		List<Evento> eventos = er.findEventosByEmail(usuario.getEmail());
		 if(!eventos.isEmpty()) {
			 attributes.addFlashAttribute("mensagem", "Você precisa deletar seus eventos na pagina Meus Eventos "
			 		+ "para excluir a conta. Quantidade de Eventos: "+eventos.size());
			 return "redirect:/teste";
		 }		
		ur.deletarUsuarioEvento(usuario.getEmail());		
		ur.deletarUsuarioRole(usuario.getId());
		ur.deletarUsuario(usuario.getId());
		return "redirect:/logout";
	}	
}
