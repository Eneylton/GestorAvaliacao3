package com.java.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Questionario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String titulo;
	private Empresa empresa;
	private Date data;
	private List<Participantes> participantes;

	private AvaliacaoParticipantes avaliacaoParticipantes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Participantes> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participantes> participantes) {
		this.participantes = participantes;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public AvaliacaoParticipantes getAvaliacaoParticipantes() {
		return avaliacaoParticipantes;
	}

	public void setAvaliacaoParticipantes(AvaliacaoParticipantes avaliacaoParticipantes) {
		this.avaliacaoParticipantes = avaliacaoParticipantes;
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
		Questionario other = (Questionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}