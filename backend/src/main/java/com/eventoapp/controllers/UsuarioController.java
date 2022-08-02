package com.eventoapp.controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Usuario;
import com.eventoapp.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	UsuarioRepository ur;
	
	@GetMapping("/register")
	public String pageUserRegister() {
		return "user/register";
	}
	
//	@PostMapping("registser")
	@RequestMapping(value="/register", method=RequestMethod.POST)
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
		ur.InsertRole(u.getId(), 3); // 3 é o perfil ROLE_USER. previamente cadastrado no banco
		attributes.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso!"+ " Nome: " +u.getNome());
		return "redirect:/register";
	}

}
