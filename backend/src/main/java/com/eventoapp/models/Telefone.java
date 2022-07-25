package com.eventoapp.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTelefone;
	
	private String tipo;
	
	@NotEmpty
	private String ddd;
	
	@NotEmpty (message = "Campo Número de telefone não pode está vazio")
	private String numero;
	
	@JoinColumn(name= "id_participante")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Participante participante;

	public Long getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(Long idTelefone) {
		this.idTelefone = idTelefone;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}		

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}	
	
}
