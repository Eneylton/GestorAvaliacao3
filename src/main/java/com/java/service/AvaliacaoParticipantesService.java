package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.AvaliacaoParticipantesDAO;
import com.java.modelo.AvaliacaoParticipantes;

public class AvaliacaoParticipantesService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AvaliacaoParticipantesDAO avaliacaoParticipantesDAO;

	public AvaliacaoParticipantes retornarAvaliacaoParticipantesPorID(Long id)
			throws ClassNotFoundException, SQLException {
		return avaliacaoParticipantesDAO.retornarAvaliacaoParticipantesPorID(id);
	}

	public List<AvaliacaoParticipantes> listarTodos() throws SQLException {
		return avaliacaoParticipantesDAO.listarTodos();
	}

	public void incluir(AvaliacaoParticipantes avaliacaoParticipantes) throws SQLException {
		avaliacaoParticipantesDAO.incluir(avaliacaoParticipantes);
	}

	public void alterar(AvaliacaoParticipantes avaliacaoParticipantes) throws SQLException {
		avaliacaoParticipantesDAO.alterar(avaliacaoParticipantes);
	}

	public void excluir(AvaliacaoParticipantes avaliacaoParticipantes) throws SQLException {
		avaliacaoParticipantesDAO.excluir(avaliacaoParticipantes);
	}

}
