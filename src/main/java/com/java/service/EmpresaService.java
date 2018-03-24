package com.java.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.java.dao.EmpresaDAO;
import com.java.modelo.Empresa;

public class EmpresaService implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private EmpresaDAO empresaDAO = new EmpresaDAO();
	
	
	public Empresa retornarEmpresaPorID(Long id) throws ClassNotFoundException, SQLException {
		return empresaDAO.retornarEmpresaPorID(id);
	}
	
	public List<Empresa> listarUsuariosPorEmpresa(Long id) throws SQLException {
		return empresaDAO.listarUsuariosPorEmpresa(id);
	}
	
	public List<Empresa> listarUsuarioPorEmpresa() throws SQLException {
		return empresaDAO.listarUsuarioPorEmpresa();
	}

	public List<Empresa> listarTodos() throws SQLException {
		return empresaDAO.listarTodos();
	}
	
	public void incluir(Empresa empresa) throws SQLException {
		empresaDAO.incluir(empresa);
	}

	public void alterar(Empresa empresa) throws SQLException {
		empresaDAO.alterar(empresa);
	}

	public void excluir(Empresa empresa) throws SQLException {
		empresaDAO.excluir(empresa);
	}
		
}