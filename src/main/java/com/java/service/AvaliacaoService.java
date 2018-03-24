package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.AvaliacaoDAO;
import com.java.modelo.Participantes;

public class AvaliacaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AvaliacaoDAO avaliacaoDAO;

	public Participantes retornarParticipantesPorID(Long id) {
		return avaliacaoDAO.retornarParticipantesPorID(id);
	}

	@SuppressWarnings("rawtypes")
	public List getQuestaoERespostas(Long id, Long idQuest) throws SQLException {
		return avaliacaoDAO.getQuestaoERespostas(id, idQuest);
	}

	public void registraAberta(Long id, int idResposta, String resposta) {
		avaliacaoDAO.registraAberta(id, idResposta, resposta);
	}

	public void registraResposta(Long id, int idResposta) {
		avaliacaoDAO.registraResposta(id, idResposta);
	}
	
	public void registraContador(Long id) {
		avaliacaoDAO.registraContador(id);
	}

}
