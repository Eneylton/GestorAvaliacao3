package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.QuestionarioDAO;
import com.java.modelo.Participantes;
import com.java.modelo.Questionario;
import com.java.util.NegocioException;

public class QuestionarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private QuestionarioDAO questionarioDAO;

	public Questionario retornarQuestionarioPorID(Long id) throws ClassNotFoundException, SQLException {
		return questionarioDAO.retornarQuestionarioPorID(id);
	}
	
	public List<Questionario> listarTodosPorEmpresas(Long id) throws SQLException {
		return questionarioDAO.listarTodosPorEmpresas(id);
	}

	public List<Questionario> listarTodos() throws SQLException {
		return questionarioDAO.listarTodos();
	}

	@SuppressWarnings("unused")
	private List<Participantes> listarParticipantesPorAvaliacao2(Questionario questionario) throws Exception {
		if (questionario == null) {
			throw new NegocioException("O Participate deve ser informada.");
		}
		return questionarioDAO.listarParticipantesPorAvaliacao2(questionario);
	}

	public void adicionarParticipantesParaAvaliacao(Questionario questionario, List<Participantes> participantes)
			throws SQLException, NegocioException {

		if (questionario.getId() == null || questionario.getId() == 0) {
			throw new NegocioException("Participante inválido.");
		}

		if (participantes == null || participantes.size() == 0) {
			throw new NegocioException("Selecione pelo menos um participante.");
		}

		questionarioDAO.adicionarParticipantesParaAvaliacao(questionario, participantes);
	}

	public List<Questionario> avaliacaoPDF(Long id) throws Exception {
		
		return questionarioDAO.avaliacaoPDF(id);
  	}

	public void incluir(Questionario questionario) throws SQLException {
		questionarioDAO.incluir(questionario);
	}

	public void alterar(Questionario questionario) throws SQLException {
		questionarioDAO.alterar(questionario);
	}

	public void excluir(Questionario questionario) throws SQLException {
		questionarioDAO.excluir(questionario);
	}

}
