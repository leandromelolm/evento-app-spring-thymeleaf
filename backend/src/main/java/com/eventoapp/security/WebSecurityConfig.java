package com.eventoapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementsUserDetailsService userDailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/home", "/","/eventos","/participantes","/evento/**", "/teste", "/register").permitAll()
			.antMatchers(HttpMethod.GET, "/cadastrarEvento").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.POST, "/cadastrarEvento").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.POST, "/evento/**").permitAll()
			.antMatchers(HttpMethod.POST, "/register").permitAll()
			.antMatchers(HttpMethod.GET, "/deletar").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/deletarParticipante").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/deletarParticipantePageParticipantes").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login.html")
			.failureUrl("/login-error.html")
			.successForwardUrl("/home").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
//		auth.inMemoryAuthentication()
//		.withUser("melo").password(passwordEncoder().encode("123456")).roles("User")
//		.and()
//        .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	}

	@Override
	public void configure(WebSecurity web) throws java.lang.Exception{
		web.ignoring().antMatchers("/materialize/**", "/style/**", "/js/**","/h2-console/**");
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
