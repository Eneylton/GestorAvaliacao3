package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Setor;

public class SetorDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public Setor retornarSetorPorID(Long id) throws ClassNotFoundException, SQLException {

		String sql = "select id,descricao from setor where id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Setor setor = null;

		while (rs.next()) {

			setor = new Setor();

			setor.setId(rs.getLong("id"));
			setor.setDescricao(rs.getString("descricao"));

		}

		stmt.close();
		con.close();

		return setor;

	}

	public List<Setor> listarTodos() throws SQLException {

		List<Setor> lista = new ArrayList<Setor>();

		String sql = "select id,descricao from setor order by id desc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Setor setor = null;

		while (rs.next()) {

			setor = new Setor();

			setor.setId(rs.getLong("id"));
			setor.setDescricao(rs.getString("descricao"));

			lista.add(setor);

		}

		stmt.close();
		con.close();

		return lista;

	}

	public void incluir(Setor setor) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO setor (descricao) " + " VALUES (?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, setor.getDescricao());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(Setor setor) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "update setor set descricao=? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, setor.getDescricao());
		stmt.setLong(2, setor.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(Setor setor) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from setor where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, setor.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}
