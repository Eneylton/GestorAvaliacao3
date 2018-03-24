package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.ParticipantesDAO;
import com.java.modelo.Participantes;
import com.java.modelo.Questionario;

public class ParticipantesService implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Inject
	private ParticipantesDAO participantesDAO;

	public Participantes retornarParticipantesPorID(Long id) throws ClassNotFoundException, SQLException {
		return participantesDAO.retornarParticipantesPorID(id);
	}
	
	public List<Questionario> listarTodosParticipantesPorQuestionario(Long id) throws SQLException {
		return participantesDAO.listarTodosParticipantesPorQuestionario(id);
	}
	
	public List<Participantes> listarTodosPorEmpresa(Long id) throws SQLException {
		return participantesDAO.listarTodosPorEmpresa(id);
	}

	public List<Participantes> listarTodos() throws SQLException {
		return participantesDAO.listarTodos();
	}
	
	public void incluir(Participantes participantes) throws SQLException {
		participantesDAO.incluir(participantes);
	}

	public void alterar(Participantes participantes) throws SQLException {
		participantesDAO.alterar(participantes);
	}

	public void excluir(Participantes participantes) throws SQLException {
		participantesDAO.excluir(participantes);
	}

}
