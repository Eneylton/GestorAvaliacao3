package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.AvaliacaoParticipantes;

public class AvaliacaoParticipantesDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection con;

	public AvaliacaoParticipantes retornarAvaliacaoParticipantesPorID(Long id) throws ClassNotFoundException, SQLException {

		String sql = "SELECT id,participantes_id,questionario_id FROM participantes_avaliacao  where id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		AvaliacaoParticipantes avaliacaoParticipantes = null;

		while (rs.next()) {

			avaliacaoParticipantes = new AvaliacaoParticipantes();
			avaliacaoParticipantes.setId(rs.getLong("id"));
			
		

		}

		stmt.close();
		con.close();

		return avaliacaoParticipantes;

	}

	public List<AvaliacaoParticipantes> listarTodos() throws SQLException {

		List<AvaliacaoParticipantes> lista = new ArrayList<AvaliacaoParticipantes>();

		String sql = "SELECT id,participantes_id,questionario_id FROM participantes_avaliacao  order by id desc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		AvaliacaoParticipantes avaliacaoParticipantes = null;

		while (rs.next()) {

			avaliacaoParticipantes = new AvaliacaoParticipantes();
			avaliacaoParticipantes.setId(rs.getLong("id"));
			lista.add(avaliacaoParticipantes);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	

	public void incluir(AvaliacaoParticipantes avaliacaoParticipantes) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO participantes_avaliacao (participantes_id, questionario_id) " + "VALUES (?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, avaliacaoParticipantes.getParticipantes().getId());
		stmt.setLong(2, avaliacaoParticipantes.getQuestionario().getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(AvaliacaoParticipantes avaliacaoParticipantes) throws SQLException {

		con = new ConnectionFactory().getConnection();

		/*
		 * SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); String
		 * dataFormatada = dt1.format(AvaliacaoParticipantes.getData());
		 */

		String sql = "update participantes_avaliacao set " + "participantes_id=? , questionario_id=?  where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, avaliacaoParticipantes.getParticipantes().getId());
		stmt.setLong(2, avaliacaoParticipantes.getQuestionario().getId());
		stmt.setLong(3, avaliacaoParticipantes.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(AvaliacaoParticipantes avaliacaoParticipantes) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from participantes_avaliacao where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, avaliacaoParticipantes.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}
