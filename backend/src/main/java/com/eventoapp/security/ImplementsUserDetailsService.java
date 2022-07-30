package com.eventoapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Usuario;
import com.eventoapp.repository.UserRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = ur.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return user;
	}

}
