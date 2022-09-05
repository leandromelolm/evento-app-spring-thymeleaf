package com.eventoapp.models;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.eventoapp.models.enums.StatusEvento;

public class EventoListPagDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;	

	private Long codigo;
	private String nome;	
	private String local;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;
	private String horario;		
	private Integer quantParticip;
	private int quantMaxParticip;	
	private String emailResponsavelEvento;	
	private Integer status;
	
	public EventoListPagDTO() {		
	}
		
	public EventoListPagDTO(Evento e) {
		super();
		this.codigo = e.getCodigo();
		this.nome = e.getNome();
		this.local = e.getLocal();
		this.data = e.getData();
		this.horario = e.getHorario();		
		this.quantParticip = e.getQuantParticip();
		this.quantMaxParticip = e.getQuantMaxParticip();		
		this.emailResponsavelEvento = e.getEmailResponsavelEvento();
		this.status = e.getStatus().getCod();
	}

	public EventoListPagDTO(Evento e, String hideResponsavel) {		
		this.codigo = e.getCodigo();
		this.nome = e.getNome();
		this.local = e.getLocal();
		this.data = e.getData();
		this.horario = e.getHorario();		
		this.quantParticip = e.getQuantParticip();
		this.quantMaxParticip = e.getQuantMaxParticip();		
		this.emailResponsavelEvento = e.getEmailResponsavelEvento().substring(0,3)+"*";
		this.status = e.getStatus().getCod();
	}
	
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
	public Integer getQuantParticip() {
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
	public String getEmailResponsavelEvento() {
		return emailResponsavelEvento;
	}
	public void setEmailResponsavelEvento(String emailResponsavelEvento) {
		this.emailResponsavelEvento = emailResponsavelEvento;
	}
	public StatusEvento getStatus() {
		return StatusEvento.toEnum(status);
	}
}
