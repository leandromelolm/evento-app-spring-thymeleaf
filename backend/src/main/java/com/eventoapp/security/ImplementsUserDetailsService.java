package com.eventoapp.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Usuario;
import com.eventoapp.repository.UserRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = ur.findByEmail(email.toLowerCase());
		
		if(user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return new User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
	}

}
