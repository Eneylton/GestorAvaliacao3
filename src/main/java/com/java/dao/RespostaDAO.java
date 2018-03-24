package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Resposta;

public class RespostaDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public Resposta retornarRespostaPorID(Long id) throws ClassNotFoundException, SQLException {

		String sql = "select id,resposta,numeroquestao_id,questionario_id,questao_id from respostas where id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Resposta Resposta = null;

		while (rs.next()) {

			Resposta = new Resposta();

			Resposta.setId(rs.getLong("id"));
			Resposta.setResposta(rs.getString("resposta"));

		}

		stmt.close();
		con.close();

		return Resposta;

	}

	public List<Resposta> listarTodos() throws SQLException {

		List<Resposta> lista = new ArrayList<Resposta>();

		String sql = "select id,resposta,numeroquestao_id,questionario_id from respostas order by id desc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Resposta Resposta = null;

		while (rs.next()) {

			Resposta = new Resposta();

			Resposta.setId(rs.getLong("id"));
			Resposta.setResposta(rs.getString("resposta"));

			lista.add(Resposta);

		}

		stmt.close();
		con.close();

		return lista;

	}

	public void registrarLista(List<Resposta> lista) throws SQLException {
		

		try {

		con = new ConnectionFactory().getConnection();

		con.setAutoCommit(false);
		

		for (Resposta quest : lista) {

			String sql = "INSERT INTO respostas (resposta,numeroquestao_id,questionario_id,questao_id,contador) " 
			+ "VALUES (?,?,?,?,?)";

			PreparedStatement stmt;

			stmt = con.prepareStatement(sql);

			stmt.setString(1, quest.getResposta());
			stmt.setInt(2, quest.getNumeroQuestao());
			stmt.setInt(3, quest.getQuestionario());
			stmt.setInt(4, quest.getQuestaoID());
			stmt.setInt(5, 0);

			stmt.execute();
			stmt.close();
		

		}

		con.commit();

	} catch (Exception e) {
		con.rollback();
	}

}

	public void incluir(Resposta resposta) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO respostas (resposta,numeroquestao_id,questionario_id,questao_id,contador) "
		           + " VALUES (?,?,?,?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, resposta.getResposta());
		stmt.setInt(2, resposta.getNumeroQuestao());
		stmt.setInt(3, resposta.getQuestionario());
		stmt.setInt(4, resposta.getQuestaoID());
		stmt.setInt(5, 0);

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(Resposta resposta) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "update respostas set resposta=?,questao_id=? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, resposta.getResposta());
		stmt.setLong(2, resposta.getQuestao().getId());
		stmt.setLong(3, resposta.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(Resposta resposta) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from respostas where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, resposta.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}
