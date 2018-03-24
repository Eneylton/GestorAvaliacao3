package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.QuestaoDAO;
import com.java.modelo.Questao;
import com.java.modelo.Resposta;

public class QuestaoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Inject
	private QuestaoDAO questaoDAO;

	public Questao retornarQuestaoPorID(Long id) throws ClassNotFoundException, SQLException {
		return questaoDAO.retornarQuestaoPorID(id);
	}

	public List<Questao> listarTodos() throws SQLException {
		return questaoDAO.listarTodos();
	}
	
	public List<Questao> listarTodosPorQuestionario(Long id) throws SQLException {
		return questaoDAO.listarTodosPorQuestionario(id);
	}
	
	public List<Resposta> buscarRespostas(Questao questao) throws SQLException {
		return questaoDAO.buscarRespostas(questao);
	}
	
	
	public List<Resposta> buscarRespostasRetorno(Long id, Long idQuestao,int idResp) throws SQLException {
		return questaoDAO.buscarRespostasRetorno(id,idQuestao,idResp);
	}
	
	public void incluir(Questao questao) throws SQLException {
		questaoDAO.incluir(questao);
	}

	public void alterar(Questao questao) throws SQLException {
		questaoDAO.alterar(questao);
	}

	public void excluir(Questao questao) throws SQLException {
		questaoDAO.excluir(questao);
	}

}
