package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;
import com.java.modelo.Resposta;
import com.java.modelo.Setor;

public class QuestaoDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public Questao retornarQuestaoPorID(Long id) throws ClassNotFoundException, SQLException {

		String sql = "select id,questao,numeroquestao_id,questionario_id,setor_id,checkin,subjetivas from questao where id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Questao questao = null;

		while (rs.next()) {

			questao = new Questao();

			questao.setId(rs.getLong("id"));
			questao.setQuestao(rs.getString("questao"));
			questao.setNumeroQuestao_id(rs.getInt("numeroquestao_id"));
			questao.setCheckin(rs.getInt("checkin"));
			questao.setSubjetivas(rs.getInt("subjetivas"));

			Setor setor = new Setor();

			setor.setId(rs.getLong("setor_id"));
			questao.setSetor(setor);

			Questionario quest = new Questionario();
			quest.setId(rs.getLong("questionario_id"));
			questao.setQuestionario(quest);

			List<Resposta> listResposta = this.buscarRespostas(questao);
			questao.setRespostas(listResposta);

		}

		stmt.close();
		con.close();

		return questao;

	}

	public List<Resposta> buscarRespostas(Questao questao) throws SQLException {
		List<Resposta> lista = new ArrayList<Resposta>();

		String sql = "select q.id,r.resposta " + "from questao as q inner join respostas as r on "
				+ "(q.numeroquestao_id = r.numeroquestao_id) where q.id = ? order by q.id asc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, questao.getId());

		ResultSet rs = stmt.executeQuery();

		Resposta respostas = null;

		while (rs.next()) {

			respostas = new Resposta();

			respostas.setId(rs.getLong("id"));
			respostas.setResposta(rs.getString("resposta"));

			lista.add(respostas);

		}

		stmt.close();
		con.close();

		return lista;

	}

	public List<Resposta> buscarRespostasRetorno(Long id, Long idQuestao,int idResp) throws SQLException {
		List<Resposta> lista = new ArrayList<Resposta>();

		String sql = "select q.id,r.resposta   from questao as q inner join respostas as r on "
				 + "(q.numeroquestao_id = r.numeroquestao_id) inner join questionario as qe "
				 + " on (qe.id = q.questionario_id) where qe.id  = ? and q.numeroquestao_id = ?"
				 + " and r.questao_id = ? order by q.id asc";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);
		stmt.setInt(2, idResp);
		stmt.setLong(3, idQuestao);

		ResultSet rs = stmt.executeQuery();

		Resposta respostas = null;

		while (rs.next()) {

			respostas = new Resposta();

			respostas.setId(rs.getLong("id"));
			respostas.setResposta(rs.getString("resposta"));

			lista.add(respostas);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	public List<Questao> listarTodos() throws SQLException {

		List<Questao> lista = new ArrayList<Questao>();

		String sql = "select " + "q.id," + "q.questao, " + "q.numeroquestao_id," + "q.checkin," + "q.subjetivas," + "q.questionario_id,"
				+ "qe.id as idQuest," + "qe.titulo," + "s.id as setor_id," + "s.descricao as descricao " + "from "
				+ "questao AS q " + "inner join " + "questionario as qe on (q.questionario_id = qe.id) " + "inner join "
				+ "setor as s ON (s.id = q.setor_id) " + "order by q.id asc";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		

		ResultSet rs = stmt.executeQuery();

		Questao questao = null;

		while (rs.next()) {

			questao = new Questao();

			questao.setId(rs.getLong("id"));
			questao.setQuestao(rs.getString("questao"));
			questao.setNumeroQuestao_id(rs.getInt("numeroquestao_id"));
			questao.setCheckin(rs.getInt("checkin"));
			questao.setSubjetivas(rs.getInt("subjetivas"));

			Setor setor = new Setor();

			setor.setId(rs.getLong("setor_id"));
			setor.setDescricao(rs.getString("descricao"));
			questao.setSetor(setor);

			Questionario quest = new Questionario();
			quest.setId(rs.getLong("idQuest"));
			quest.setTitulo(rs.getString("titulo"));
			questao.setQuestionario(quest);

			lista.add(questao);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	public List<Questao> listarTodasQuestoesporEmpresa(Long id) throws SQLException {

		List<Questao> lista = new ArrayList<Questao>();

		String sql = "select " + "q.id," + "q.questao, " + "q.numeroquestao_id," + "q.checkin," + "q.subjetivas," + "q.questionario_id,"
				+ "qe.id as idQuest," + "qe.titulo," + "s.id as setor_id," + "s.descricao as descricao " + "from "
				+ "questao as q " + "inner join " + "questionario as qe on (q.questionario_id = qe.id) " + "inner join "
				+ "setor as s on (s.id = q.setor_id) " + "order by q.id asc";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);
		

		ResultSet rs = stmt.executeQuery();

		Questao questao = null;

		while (rs.next()) {

			questao = new Questao();

			questao.setId(rs.getLong("id"));
			questao.setQuestao(rs.getString("questao"));
			questao.setNumeroQuestao_id(rs.getInt("numeroquestao_id"));
			questao.setCheckin(rs.getInt("checkin"));
			questao.setSubjetivas(rs.getInt("subjetivas"));

			Setor setor = new Setor();

			setor.setId(rs.getLong("setor_id"));
			setor.setDescricao(rs.getString("descricao"));
			questao.setSetor(setor);

			Questionario quest = new Questionario();
			quest.setId(rs.getLong("idQuest"));
			quest.setTitulo(rs.getString("titulo"));
			questao.setQuestionario(quest);

			lista.add(questao);

		}

		stmt.close();
		con.close();

		return lista;

	}

	

	public List<Questao> listarTodosPorQuestionario(Long id) throws SQLException {

		List<Questao> lista = new ArrayList<Questao>();

		String sql = "select " + "q.id," + "q.questao, " + "q.numeroquestao_id," + "q.checkin," + "q.subjetivas," + "q.questionario_id,"
				+ "qe.id as idQuest," + "qe.titulo," + "s.id as setor_id," + "s.descricao as descricao " + "from "
				+ "questao as q " + "inner join " + "questionario as qe on (q.questionario_id = qe.id) " + "inner join "
				+ "setor as s on (s.id = q.setor_id) where q.questionario_id = ? " + "order by q.id asc";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Questao questao = null;

		while (rs.next()) {

			questao = new Questao();

			questao.setId(rs.getLong("id"));
			questao.setQuestao(rs.getString("questao"));
			questao.setNumeroQuestao_id(rs.getInt("numeroquestao_id"));
			questao.setCheckin(rs.getInt("checkin"));
			questao.setSubjetivas(rs.getInt("subjetivas"));

			Setor setor = new Setor();

			setor.setId(rs.getLong("setor_id"));
			setor.setDescricao(rs.getString("descricao"));
			questao.setSetor(setor);

			Questionario quest = new Questionario();
			quest.setId(rs.getLong("idQuest"));
			quest.setTitulo(rs.getString("titulo"));
			questao.setQuestionario(quest);

			lista.add(questao);

		}

		stmt.close();
		con.close();

		return lista;

	}

	public void registrarLista(List<Questao> lista) throws SQLException {

		try {

			con = new ConnectionFactory().getConnection();

			con.setAutoCommit(false);

			for (Questao quest : lista) {

				String sql = "INSERT INTO questao (questao,numeroquestao_id,questionario_id,setor_id,checkin,subjetivas) " 
				           + "VALUES (?,?,?,?,?,?)";

				PreparedStatement stmt;

				stmt = con.prepareStatement(sql);

				stmt.setString(1, quest.getQuestao());
				stmt.setInt(2, quest.getNumeroQuestao());
				stmt.setLong(3, quest.getQuestionario().getId());
				stmt.setLong(4, quest.getSetor().getId());
				stmt.setInt(5, quest.getCheckin());
				stmt.setInt(6, quest.getSubjetivas());

				stmt.execute();
				stmt.close();

			}

			con.commit();

		} catch (Exception e) {
			con.rollback();
		}

	}
	
	

	public void incluir(Questao questao) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO questao (questao,numeroquestao_id,questionario_id,setor_id,checkin,subjetivas) " 
		           + " VALUES (?,?,?,?,?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, questao.getQuestao());
		stmt.setInt(2, questao.getNumeroQuestao());
		stmt.setLong(3, questao.getQuestionario().getId());
		stmt.setLong(4, questao.getSetor().getId());
		stmt.setInt(5, questao.getCheckin());
		stmt.setInt(6, questao.getSubjetivas());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(Questao questao) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "update questao set questao=?,questionario_id=? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, questao.getQuestao());
		stmt.setLong(2, questao.getQuestionario().getId());
		stmt.setLong(3, questao.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(Questao questao) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from questao where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, questao.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}
