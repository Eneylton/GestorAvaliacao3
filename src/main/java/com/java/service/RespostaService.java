package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.RespostaDAO;
import com.java.modelo.Resposta;

public class RespostaService implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Inject
	private RespostaDAO respostaDAO;

	public Resposta retornarRespostaPorID(Long id) throws ClassNotFoundException, SQLException {
		return respostaDAO.retornarRespostaPorID(id);
	}

	public List<Resposta> listarTodos() throws SQLException {
		return respostaDAO.listarTodos();
	}
	
	public void incluir(Resposta resposta) throws SQLException {
		respostaDAO.incluir(resposta);
	}

	public void alterar(Resposta resposta) throws SQLException {
		respostaDAO.alterar(resposta);
	}

	public void excluir(Resposta resposta) throws SQLException {
		respostaDAO.excluir(resposta);
	}

}
