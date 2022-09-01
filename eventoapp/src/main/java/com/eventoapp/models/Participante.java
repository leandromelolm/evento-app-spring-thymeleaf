package com.eventoapp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Participante implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)	
	private Long idParticipante;
	
	@NotEmpty (message = "O campo Nome não pode ser vazio")	
	@Size(min=5, max=40, message="O campo nome deve ter entre 5 e 40 caracteres")
	private String nomeParticipante;
	
	@NotBlank (message = "O campo Email não pode estar em branco")
	private String email;
	
	private String CPF;
	
	@NotNull (message = "O campo Data de Nascimento não pode ser nulo")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") 
	private Date dataCadastro;
	
	@OneToMany(mappedBy = "participante", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Telefone> telefones;	
	
	@ManyToOne	
	private Evento evento;
	
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
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	@Override
	public int hashCode() {
		return Objects.hash(CPF, dataCadastro, dataNascimento, email, evento, idParticipante, nomeParticipante,
				telefones);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participante other = (Participante) obj;
		return Objects.equals(CPF, other.CPF) && Objects.equals(dataCadastro, other.dataCadastro)
				&& Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(email, other.email)
				&& Objects.equals(evento, other.evento) && idParticipante == other.idParticipante
				&& Objects.equals(nomeParticipante, other.nomeParticipante)
				&& Objects.equals(telefones, other.telefones);
	}
	
}
