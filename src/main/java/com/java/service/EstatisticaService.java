package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.EstatisticaDAO;
import com.java.modelo.Estatistica;
import com.java.modelo.Questao;

public class EstatisticaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstatisticaDAO estatisticaDAO;

	public List<Estatistica> listarTodos() throws SQLException {
		return estatisticaDAO.listarTodos();
	}

	public List<Estatistica> listarTodosPorQuestao(Long id,Long idQuestao, Date dataInicio, Date dataFim) throws SQLException {
		return estatisticaDAO.listarTodosPorQuestao(id,idQuestao, dataInicio, dataFim);
	}

	public List<Questao> buscarQuestaoporQuestionario(Long id) throws SQLException {
		return estatisticaDAO.buscarQuestaoporQuestionario(id);
	}

}
