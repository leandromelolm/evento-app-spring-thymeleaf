package com.eventoapp.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.eventoapp.models.Usuario;

public class UserService {
	public static Usuario authenticated() {
		try {
			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}

}
