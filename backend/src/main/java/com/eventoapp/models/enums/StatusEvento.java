package com.eventoapp.models.enums;

public enum StatusEvento {
	
	ABERTO(1, "Aberto"),
	PAUSADO(2, "Pausado"),
	ENCERRADO(3, "Encerrado"),
	FINALIZADO(4, "Finalizado");
	
	private int cod;
	private String descricao;
	
	private StatusEvento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	public int getCod() {
		return cod;
	}
	public String getDescricao () {
		return descricao;
	}
	public static StatusEvento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (StatusEvento x : StatusEvento.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}	
}
