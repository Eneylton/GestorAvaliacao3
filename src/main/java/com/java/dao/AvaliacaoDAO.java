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
import com.java.modelo.Participantes;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;
import com.java.modelo.Resposta;
import com.java.modelo.Setor;

public class AvaliacaoDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public Participantes retornarParticipantesPorID(Long id) {

		Participantes participantes = new Participantes();

		participantes.setId(id);

		String sql = "SELECT "
				+ "p.id, p.nome,p.email, p.questoesRespondidas, qe.id as idQuest,qe.titulo,pa.id as idMix " + "FROM "
				+ "participantes AS p " + "INNER JOIN "
				+ "participantes_avaliacao AS pa ON (pa.participantes_id = p.id) "
				+ "INNER JOIN questionario as qe ON (qe.id = pa.questionario_id) where p.id = ?";

		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setLong(1, participantes.getId());

			ResultSet rs = stmt.executeQuery();

			rs.next();

			participantes.setId(rs.getLong("id"));
			participantes.setNome(rs.getString("nome"));
			participantes.setEmail(rs.getString("email"));
			participantes.setQuestoesRespondidas(rs.getInt("questoesRespondidas"));

			AvaliacaoParticipantes ap = new AvaliacaoParticipantes();
			ap.setId(rs.getLong("idMix"));
			Questionario quest = new Questionario();
			quest.setId(rs.getLong("idQuest"));
			quest.setTitulo(rs.getString("titulo"));
			ap.setQuestionario(quest);

			participantes.setAvaliacaoParticipantes(ap);

			rs.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
			participantes = null;
		}

		return participantes;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getQuestaoERespostas(Long id, Long idQuest) throws SQLException {
		List questaoCompleta = new ArrayList();

		List<Resposta> respostas = new ArrayList<Resposta>();

		Resposta resposta;

		Questao questao = new Questao();

		questao.setId(id);

		String sql = "SELECT   r.id AS idResposta,   qe.id AS idQuest,   q.numeroquestao_id, "
				+ "r.numeroquestao_id,   r.resposta,   q.questao,q.checkin,q.subjetivas,qe.titulo,s.id as idsetor,s.descricao as descricao "
				+ "FROM respostas AS r INNER JOIN   questao AS q ON (r.numeroquestao_id = q.numeroquestao_id) "
				+ "INNER JOIN questionario AS qe ON (qe.id = q.questionario_id) "
				+ "inner join setor as s on(s.id  = q.setor_id ) WHERE " + "q.numeroquestao_id = ? "
				+ "AND r.numeroquestao_id = ? " + "AND q.questionario_id = ? " + "AND r.questionario_id = ? "
				+ "ORDER BY r.id";
		try {
			con = new ConnectionFactory().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setLong(1, id);
			stmt.setLong(2, id);
			stmt.setLong(3, idQuest);
			stmt.setLong(4, idQuest);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				questao.setQuestao(rs.getString("questao"));
				questao.setNumeroQuestao_id(rs.getInt("numeroquestao_id"));
				questao.setCheckin(rs.getInt("checkin"));
				questao.setSubjetivas(rs.getInt("subjetivas"));
				

				Setor st = new Setor();
				st.setId(rs.getLong("idsetor"));
				st.setDescricao(rs.getString("descricao"));
				questao.setSetor(st);

				Questionario quest = new Questionario();
				quest.setId(rs.getLong("idQuest"));
				quest.setTitulo(rs.getString("titulo"));
				questao.setQuestionario(quest);

				resposta = new Resposta();
				resposta.setId(rs.getLong("idResposta"));
				resposta.setResposta(rs.getString("resposta"));

				respostas.add(resposta);
			}
			questaoCompleta.add(questao);
			questaoCompleta.add(respostas);

			rs.close();
			con.close();

		} catch (SQLException e) {

			System.out.println("Erro de SQL:" + e.getMessage());
		}

		return questaoCompleta;
	}
	
	public void registraContador(Long id) {
		
		con = new ConnectionFactory().getConnection();
		
		String sql = " UPDATE participantes SET contadorID = (contadorID + 1) WHERE id = ? ";
		
		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setLong(1, id);

			stmt.execute();
			stmt.close();

			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
		}
		
	}
	

	public void registraResposta(Long id, int idResposta) {

		String sql = " UPDATE respostas SET contador = (contador + 1) WHERE id = ? ";

		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, idResposta);

			stmt.execute();
			stmt.close();

			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
		}

		sql = " UPDATE participantes SET questoesRespondidas = (questoesRespondidas + 1) WHERE id = ? ";

		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setLong(1, id);

			stmt.execute();
			stmt.close();

			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
		}

	}

	public void registraAberta(Long id, int idResposta, String resposta) {

		String sql = " UPDATE respostas SET contador = (contador + 1) WHERE id = ? ";

		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, idResposta);

			stmt.execute();
			stmt.close();

			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
		}

		sql = " UPDATE participantes SET questoesRespondidas = (questoesRespondidas + 1),"
				+ "contadorID = (contadorID + 1) WHERE id = ? ";

		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setLong(1, id);

			stmt.execute();
			stmt.close();

			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
		}

		sql = "INSERT INTO respostas_abertas (respostas) VALUES (?)";

		try {

			con = new ConnectionFactory().getConnection();

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, resposta);

			stmt.execute();
			stmt.close();

			con.close();

		} catch (SQLException e) {
			System.out.println("Erro de SQL:" + e.getMessage());
		}

	}

}