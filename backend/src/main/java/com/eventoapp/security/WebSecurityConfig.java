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
	protected void configure(HttpSecurity http) throws Exception{ // 1-ADMIN, 2-POWER_USER, 3-STANDARD_USER (Adicionados no data.sql)
		http
			.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/home", "/","/eventos","/evento/**", "/teste", "/register").permitAll()
			.antMatchers(HttpMethod.GET, "/cadastrarEvento").hasAnyRole("ADMIN","STANDARD_USER")
			.antMatchers(HttpMethod.POST, "/cadastrarEvento").hasAnyRole("ADMIN","STANDARD_USER")
			.antMatchers(HttpMethod.POST, "/evento/**").permitAll()
			.antMatchers(HttpMethod.POST, "/register").permitAll()
			.antMatchers(HttpMethod.GET, "/deletar").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/user/meus-eventos/delete2").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/user/meus-eventos/delete").hasRole("STANDARD_USER")
			.antMatchers(HttpMethod.GET, "/deletarParticipante").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/deletarParticipantePageParticipantes").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login.html")
			.failureUrl("/login-error.html")
			.successForwardUrl("/home").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
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
