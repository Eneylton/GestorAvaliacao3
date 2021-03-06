package com.java.modelo;

import java.io.Serializable;

public class Resposta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String resposta;
	private String pergunta;
	private Questao questao;
	private int numeroQuestao;
	private int questaoID;
	private int questionario;

	public int getQuestaoID() {
		return questaoID;
	}

	public void setQuestaoID(int questaoID) {
		this.questaoID = questaoID;
	}

	public int getQuestionario() {
		return questionario;
	}

	public void setQuestionario(int questionario) {
		this.questionario = questionario;
	}

	private int contador;

	public int getNumeroQuestao() {
		return numeroQuestao;
	}

	public void setNumeroQuestao(int numeroQuestao) {
		this.numeroQuestao = numeroQuestao;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
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
		Resposta other = (Resposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}