package com.eventoapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // 1-ADMIN, 2-POWER_USER, 3-STANDARD_USER (Adicionados no data.sql)
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
			.permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
		return http.build();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/materialize/**", "/style/**", "/js/**","/h2-console/**");
	}	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }	
}

// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
// https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
