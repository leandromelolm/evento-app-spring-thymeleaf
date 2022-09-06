package com.eventoapp.security;

import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${token.key}")
	private String uniqueAndSecret;	

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // 1-ADMIN, 2-POWER_USER, 3-STANDARD_USER (Adicionados no data.sql)
		http
			.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/home", "/","/eventos","/evento/**","/infodesenvolvimento").permitAll()
			.antMatchers(HttpMethod.GET, "/register").anonymous()
			.antMatchers(HttpMethod.GET, "/participantes").authenticated()
			.antMatchers(HttpMethod.GET, "/cadastrarEvento").hasAnyRole("ADMIN","POWER_USER","STANDARD_USER")
			.antMatchers(HttpMethod.POST, "/cadastrarEvento").hasAnyRole("ADMIN","POWER_USER","STANDARD_USER")
			.antMatchers(HttpMethod.POST, "/evento/**").permitAll()
			.antMatchers(HttpMethod.POST, "/register").permitAll()
			.antMatchers(HttpMethod.GET, "/deletar").authenticated()
			.antMatchers(HttpMethod.POST, "/deletarMinhaContaUsuario").hasAnyRole("ADMIN","POWER_USER","STANDARD_USER")
			.antMatchers(HttpMethod.GET, "/user/meus-eventos/delete2").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/user/meus-eventos/delete").authenticated()
			.antMatchers(HttpMethod.GET, "/deletarParticipante").hasAnyRole("ADMIN","POWER_USER","STANDARD_USER")
			.antMatchers(HttpMethod.GET, "/deletarParticipantePageParticipantes").authenticated()
			.antMatchers(HttpMethod.POST, "/user/minha-conta/edit/**").authenticated()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login.html")
			.failureUrl("/login-error.html").permitAll()
			.usernameParameter("txtUsername")
            .passwordParameter("txtPassword")
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
			.and()
			.rememberMe().tokenValiditySeconds(604800).key(uniqueAndSecret).rememberMeParameter("checkRememberMe"); // 604800 segundos = 7 dias	
		return http.build();		
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/bootstrap-5.2.0-dist/**","/jquery-3.6.0-dist/**","/materialize/**", "/style/**", "/js/**","/css/**","/h2-console/**","/webjars/**");
	}	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
// https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
