package com.eventoapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Evento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long codigo;
	
	@NotEmpty (message = "O campo NOME não pode ser vazio")	
	@Size(min=5, max=50, message="O campo NOME deve ter entre 5 e 50 caracteres")
	private String nome;
	
	@NotEmpty (message = "O campo LOCAL não pode ser vazio")	
	@Size(min=3, max=50, message="O campo LOCAL deve ter entre 3 e 50 caracteres")
	private String local;	

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;
	
	@Pattern(regexp="[0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}",message="campo HORÁRIO com formato inválido")
	private String horario;
	
	@OneToMany(mappedBy = "evento", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Participante> participantes = new ArrayList<>();
	
	private Integer quantParticip;
	
	private int quantMaxParticip;
	
	@ManyToOne
	@JoinTable( 
	        name = "usuarios_eventos", 
	        joinColumns = @JoinColumn(
	          name = "evento_id", referencedColumnName = "codigo"), 
	        inverseJoinColumns = @JoinColumn(
	          name = "usuario_email", referencedColumnName = "email")) 
	private Usuario usuario;
	
	private String emailResponsavelEvento;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getEmailResponsavelEvento() {
		return emailResponsavelEvento;
	}
	public void setEmailResponsavelEvento(String emailResponsavelEvento) {
		this.emailResponsavelEvento = emailResponsavelEvento;
	}	
	public List<Participante> getParticipantes() {
		return participantes;
	}
	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}
	public int getQuantParticip() {
		return quantParticip;
	}
	public void setQuantParticip(Integer quantParticip) {		
		this.quantParticip = quantParticip;
	}	
	public int getQuantMaxParticip() {
		return quantMaxParticip;
	}
	public void setQuantMaxParticip(int quantMaxParticip) {
		this.quantMaxParticip = quantMaxParticip;
	}
	
}
