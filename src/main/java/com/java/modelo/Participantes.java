package com.java.modelo;

import java.io.Serializable;

public class Participantes implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private boolean selecionado;
	private int questoesRespondidas;
	private int contadorID;
	private AvaliacaoParticipantes avaliacaoParticipantes;
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getContadorID() {
		return contadorID;
	}

	public void setContadorID(int contadorID) {
		this.contadorID = contadorID;
	}

	public AvaliacaoParticipantes getAvaliacaoParticipantes() {
		return avaliacaoParticipantes;
	}

	public void setAvaliacaoParticipantes(AvaliacaoParticipantes avaliacaoParticipantes) {
		this.avaliacaoParticipantes = avaliacaoParticipantes;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuestoesRespondidas() {
		return questoesRespondidas;
	}

	public void setQuestoesRespondidas(int questoesRespondidas) {
		this.questoesRespondidas = questoesRespondidas;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		Participantes other = (Participantes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}