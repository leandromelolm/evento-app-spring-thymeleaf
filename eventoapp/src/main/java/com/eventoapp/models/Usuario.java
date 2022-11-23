package com.eventoapp.models;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email(message ="Email não está na formato correto")
	@Column(unique=true)
	private String email;
	
	private String senha;	
	
	@Size(min=4, max=20, message ="Nome precisa ter de 4 a 20 letras")
	@Column(unique=true)
	private String nome;
	
	private String cpf;
	private Instant dataCadastro;
	
	private boolean enabledUser;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoAcesso;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualAcesso;
	
	@ManyToMany (cascade = CascadeType.ALL)
	@JoinTable( 
	        name = "usuarios_roles", 
	        joinColumns = @JoinColumn(
	          name = "usuario_id", referencedColumnName = "id"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "role_id", referencedColumnName = "id")) 
	private List<Role> roles;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
	private List<Evento> eventos = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name ="ultimos_acessos")
	private List<String> accessesLast = new ArrayList<>();
	
	@Transient
	private String senhaRepetida;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email.toLowerCase().strip();
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome.toLowerCase().strip();
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Instant getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Instant dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Evento> getEventos() {
		return eventos;
	}
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}	
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}
	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
	public Date getAtualAcesso() {
		return atualAcesso;
	}
	public void setAtualAcesso(Date atualAcesso) {
		this.atualAcesso = atualAcesso;
	}	
	public boolean isEnabledUser() {
		return enabledUser;
	}
	public void setEnabledUser(boolean enabledUser) {
		this.enabledUser = enabledUser;
	}		

	public List<String> getAccessesLast() {
		return accessesLast;
	}
	public void setAccessesLast(List<String> accessesLast) {
		this.accessesLast = accessesLast;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return (Collection<? extends GrantedAuthority>) this.roles;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public String getSenhaRepetida() {
		return senhaRepetida;
	}
	public void setSenhaRepetida(String senhaRepetida) {
		this.senhaRepetida = senhaRepetida;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	
		
}
