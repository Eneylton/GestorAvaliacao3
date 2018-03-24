package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import com.java.dao.SetorDAO;
import com.java.modelo.Setor;

public class SetorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SetorDAO setorDAO;

	public Setor retornarSetorPorID(Long id) throws ClassNotFoundException, SQLException {
		return setorDAO.retornarSetorPorID(id);
	}

	public List<Setor> listarTodos() throws SQLException {
		return setorDAO.listarTodos();
	}

	public void incluir(Setor setor) throws SQLException {
		setorDAO.incluir(setor);
	}

	public void alterar(Setor setor) throws SQLException {
		setorDAO.alterar(setor);
	}

	public void excluir(Setor setor) throws SQLException {
		setorDAO.excluir(setor);
	}

}
