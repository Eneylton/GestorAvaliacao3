package com.java.modelo;

import java.io.Serializable;

public class RespostaAberta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String respostas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRespostas() {
		return respostas;
	}

	public void setRespostas(String respostas) {
		this.respostas = respostas;
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
		RespostaAberta other = (RespostaAberta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}