package com.eventoapp.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
	public ModelAndView pageUserRegister() {
		ModelAndView mv = new ModelAndView("user/register");
		mv.addObject("usuarioobj", new Usuario());
		return mv;
	}
	
	@PostMapping("/register")
	public ModelAndView form(@Valid Usuario u, BindingResult result, RedirectAttributes attributes) {	
		List<String> msg = new ArrayList<String>();		
		ModelAndView mv = new ModelAndView("user/register");
		if(result.hasErrors()){
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
				System.out.println("ErrorMessage__ "+ objectError.getDefaultMessage());
			}		
			//attributes.addFlashAttribute("mensagem", "Verifique os campos!  "+ msg.toString().substring(1, msg.toString().length()-1));			
			mv.addObject("msg", msg);
			mv.addObject("usuarioobj", u);
			return mv;
		}
		if (!u.getSenha().equals(u.getSenhaRepetida())) {

			mv.addObject("msg", "Senha não são iguais!");
			mv.addObject("usuarioobj", u);
			return mv;
		}
		if(!Objects.isNull(ur.findByEmail(u.getEmail()))) {

			mv.addObject("msg", "Email já cadastrado");
			mv.addObject("usuarioobj", u);
			return mv;
		}
		if(!Objects.isNull(ur.findByNome(u.getNome()))) {
			mv.addObject("msg", "Nome já cadastrado");
			mv.addObject("usuarioobj", u);
			return mv;
		}
		if(!Objects.isNull(ur.findByCpf(u.getCpf()))) {
			mv.addObject("msg", "Cpf já cadastrado");
			mv.addObject("usuarioobj", u);			
			return mv;
		}
		u.setDataCadastro(Instant.now());
		u.setEnabledUser(true);
		u.setSenha(new BCryptPasswordEncoder().encode(u.getSenha()));
		ur.save(u);
		ur.InsertRole(u.getId(), 3); // 3 É UM PERFIL PREVIAMENTE INSERIDO NO BANCO DE DADOS

		mv.addObject("msg", u.getNome()+", Seu cadastro foi realizado com sucesso!");
		mv.addObject("sucessoCadastro", "sucess");
		mv.addObject("usuarioobj", u);
		//ModelAndView mv1 = new ModelAndView("login.html");
		return mv;
	}
	
	@RequestMapping(value="/minha-conta", method=RequestMethod.GET)
	public String test() {
		return "user/minha-conta";
	}
	
	@PostMapping("/atualizar-nome-menu")
	public ModelAndView userNameMenu(@RequestBody String stringEmail, Usuario u, HttpSession session) throws UnsupportedEncodingException {
		
		String emailDecode = URLDecoder.decode(stringEmail, "UTF-8");
		ModelAndView mv = new ModelAndView("user/minha-conta");
		Usuario user = ur.findByEmail(emailDecode.replaceAll("=", ""));				
		session.setAttribute("mySessionNome", user.getNome());
		// Atualizar entrada no sistema
		user.setUltimoAcesso(user.getAtualAcesso());		  
		user.setAtualAcesso(new Date());
		ur.updateAcessoAtualUsuario(user.getId(),user.getAtualAcesso(),user.getUltimoAcesso());		
		mv.addObject("usuario", user);
		return mv;
	}
	
	@PostMapping("/info-user-logged")
	public ModelAndView infoUsuarioLogado(
			@RequestBody String stringEmail, Model model, Usuario u, RedirectAttributes attrib, HttpSession session) 
					throws UnsupportedEncodingException {		
		String emailDecode = URLDecoder.decode(stringEmail, "UTF-8");
		ModelAndView mv = new ModelAndView("user/minha-conta");
		Usuario user = ur.findByEmail(emailDecode.replaceAll("=", ""));		
		
		session.setAttribute("mySessionNome", user.getNome());
		session.setAttribute("mySessionCpf", user.getCpf());
		session.setAttribute("mySessionDataCadastro", user.getDataCadastro());
		session.setAttribute("mySessionTipoPerfil", user.getRoles().get(0).getNameRole());
		session.setAttribute("mySessionUsuarioAtivo", user.isEnabled());
		session.setAttribute("mySessionId", user.getId());
		session.setAttribute("ultimoAcesso", user.getUltimoAcesso());
		session.setAttribute("atualAcesso", user.getAtualAcesso());
		
		List<Evento> eventos = er.findEventosByEmail(user.getEmail());		
		session.setAttribute("userQuantEventos", eventos.size());		
		//model.addAttribute("usuarioModel", user);	
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


//https://javabycode.com/sf/spring-boot-tutorial/spring-boot-thymeleaf-ajax-example.html	
