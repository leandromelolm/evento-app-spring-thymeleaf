package com.eventoapp.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Evento;
import com.eventoapp.models.Usuario;
import com.eventoapp.repository.EventoRepository;
import com.eventoapp.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	UsuarioRepository ur;
	
	@Autowired
	private EventoRepository er;
	
	@GetMapping("/register")
	public String pageUserRegister() {
		return "user/register";
	}
	
	@PostMapping("/register")
//	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String form(@Valid Usuario u, BindingResult result, RedirectAttributes attributes) {	
		List<String> msg = new ArrayList<String>();		
		if(result.hasErrors()){
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
				System.out.println("ErrorMessage__ "+ objectError.getDefaultMessage());
			}		
			attributes.addFlashAttribute("mensagem", "Verifique os campos!  "+ msg.toString().substring(1, msg.toString().length()-1));			
			return "redirect:/register";
		}
		if (!u.getSenha().equals(u.getSenhaRepetida())) {
			attributes.addFlashAttribute("mensagem", "Senha não são iguais!");			
			return "redirect:/register";
		}
		if(!Objects.isNull(ur.findByEmail(u.getEmail()))) {
			attributes.addFlashAttribute("mensagem", "Email já cadastrado");
			return "redirect:/register";
		}		
		u.setDataCadastro(Instant.now());
		u.setEnabledUser(true);
		u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
		ur.save(u);
		ur.InsertRole(u.getId(), 3); // 3 É UM PERFIL PREVIAMENTE INSERIDO NO BANCO DE DADOS
		attributes.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso!"+ " Nome: " +u.getNome());
		return "redirect:/login.html";
	}
	
	@RequestMapping(value="/minha-conta", method=RequestMethod.GET)
	public String test() {
		return "/user/minha-conta";
	}
	
//	https://javabycode.com/sf/spring-boot-tutorial/spring-boot-thymeleaf-ajax-example.html	
//	@RequestMapping(value="/info-user-logged", method=RequestMethod.POST)
	@PostMapping("/info-user-logged")
	public ModelAndView infoUsuarioLogado(@RequestBody String nome, Model model, Usuario u, RedirectAttributes attrib, HttpSession session) throws UnsupportedEncodingException {
		System.out.println("metodo testPost()...");
		String nomeDecode = URLDecoder.decode(nome, "UTF-8");
		System.out.println("nome: "+ nome +"  nomeDecode: "+ nomeDecode.replaceAll("=","") );
//		ModelAndView mv = new ModelAndView("redirect:/user/minha-conta");
		ModelAndView mv = new ModelAndView("user/minha-conta");
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
//		return "redirect:/user/minha-conta";
	}
	
	@PostMapping("/deletarMinhaContaUsuario")
	public String deletarMinhaContaUsuario(@ModelAttribute("id")long id, 
			BindingResult bindingResult, RedirectAttributes attributes) {
		Usuario usuario = ur.findById(id);
		List<Evento> eventos = er.findEventosByEmail(usuario.getEmail());
		 if(!eventos.isEmpty()) {
			 attributes.addFlashAttribute("mensagem", "Você precisa deletar seus eventos na pagina Meus Eventos "
			 		+ "para excluir a conta. Quantidade de Eventos: "+eventos.size());
			 return "redirect:/minha-conta";
		 }		
		ur.deletarUsuarioEvento(usuario.getEmail());		
		ur.deletarUsuarioRole(usuario.getId());
		ur.deletarUsuario(usuario.getId());
		return "redirect:/logout";
	}	

}
