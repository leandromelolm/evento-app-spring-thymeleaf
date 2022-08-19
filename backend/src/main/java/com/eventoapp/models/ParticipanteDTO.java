package com.eventoapp.models;

import java.io.Serializable;
import java.util.Date;

public class ParticipanteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idParticipante;	
	private String nomeParticipante;
	private String email;	
	private String CPF;	
	private Date dataNascimento;
	private Date dataCadastro;
	
	private String nomeParticipanteHidden;
	private String emailHidden;
	
	private String nomeEvento;
	private String emailResponsavelEvento;
	private Evento evento;
	
	public ParticipanteDTO(Participante p) {
		idParticipante = p.getIdParticipante();
		nomeParticipante = p.getNomeParticipante();
		email = p.getEmail();
		CPF = p.getCPF();
		dataNascimento = p.getDataNascimento();
		dataCadastro = p.getDataCadastro();
		
		nomeParticipanteHidden = p.getNomeParticipante().substring(0, 3)+"***";
		emailHidden = p.getEmail().substring(0, 2)+"***";
		
		nomeEvento = p.getEvento().getNome();
		emailResponsavelEvento = p.getEvento().getEmailResponsavelEvento();		
	}
	
	public ParticipanteDTO(Participante p, long n) {
		idParticipante = p.getIdParticipante();
		nomeParticipante = p.getNomeParticipante();
		email = p.getEmail();
		CPF = p.getCPF();
		dataNascimento = p.getDataNascimento();
		dataCadastro = p.getDataCadastro();
		
		nomeParticipanteHidden = p.getNomeParticipante().substring(0, 3)+"***";
		emailHidden = p.getEmail().substring(0, 2)+"***";
		
		nomeEvento = p.getEvento().getNome();
		emailResponsavelEvento = p.getEvento().getEmailResponsavelEvento();
		evento = p.getEvento();
	}

	public Long getIdParticipante() {
		return idParticipante;
	}

	public void setIdParticipante(Long idParticipante) {
		this.idParticipante = idParticipante;
	}

	public String getNomeParticipante() {
		return nomeParticipante;
	}

	public void setNomeParticipante(String nomeParticipante) {
		this.nomeParticipante = nomeParticipante;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNomeParticipanteHidden() {
		return nomeParticipanteHidden;
	}

	public void setNomeParticipanteHidden(String nomeParticipanteHidden) {
		this.nomeParticipanteHidden = nomeParticipanteHidden;
	}

	public String getEmailHidden() {
		return emailHidden;
	}

	public void setEmailHidden(String emailHidden) {
		this.emailHidden = emailHidden;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public String getEmailResponsavelEvento() {
		return emailResponsavelEvento;
	}

	public void setEmailResponsavelEvento(String emailResponsavelEvento) {
		this.emailResponsavelEvento = emailResponsavelEvento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}	
}
