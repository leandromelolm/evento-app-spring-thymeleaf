package com.eventoapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventoapp.models.Usuario;
import com.eventoapp.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
	private UsuarioRepository usuarioRepository;
    
    public Usuario usuarioLogado(){
        Usuario usuarioLogado = usuarioRepository.findByEmail(UserService.authenticated().getUsername());
        return usuarioLogado;
    }
}
