package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Empresa;
import com.java.modelo.Participantes;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;
import com.java.modelo.Resposta;
import com.java.modelo.Setor;

public class QuestionarioDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public Questionario retornarQuestionarioPorID(Long id) throws ClassNotFoundException, SQLException {

		String sql = "select id,titulo,empresa_id from questionario where id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Questionario questionario = null;

		while (rs.next()) {

			questionario = new Questionario();

			questionario.setId(rs.getLong("id"));
			questionario.setTitulo(rs.getString("titulo"));

			List<Participantes> participantes = this.listarAvaliacaoParticipantesRetorno(questionario);
			questionario.setParticipantes(participantes);
		}

		stmt.close();
		con.close();

		return questionario;

	}

	private List<Participantes> listarAvaliacaoParticipantesRetorno(Questionario questionario) throws SQLException {

		String sql = "SELECT  " + "p.id, p.nome, p.email " + "FROM " + "participantes AS p " + "INNER JOIN "
				+ "participantes_avaliacao AS pa ON (p.id = pa.participantes_id) " + "WHERE "
				+ "pa.questionario_id = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, questionario.getId());

		ResultSet rs = stmt.executeQuery();

		List<Participantes> lista = new ArrayList<Participantes>();

		while (rs.next()) {

			Participantes participantes = new Participantes();

			participantes.setId(rs.getLong("id"));
			participantes.setNome(rs.getString("nome"));
			participantes.setEmail(rs.getString("email"));

			lista.add(participantes);

		}

		rs.close();
		stmt.close();

		return lista;

	}
	
	
	public List<Questionario> listarTodosPorEmpresas(Long id) throws SQLException {

		List<Questionario> lista = new ArrayList<Questionario>();

		String sql = "select id,titulo,empresa_id from questionario WHERE empresa_id = ?  order by id desc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);
		

		ResultSet rs = stmt.executeQuery();

		Questionario questionario = null;

		while (rs.next()) {

			questionario = new Questionario();

			questionario.setId(rs.getLong("id"));
			questionario.setTitulo(rs.getString("titulo"));

			lista.add(questionario);

		}

		stmt.close();
		con.close();

		return lista;

	}
	

	public List<Questionario> listarTodos() throws SQLException {

		List<Questionario> lista = new ArrayList<Questionario>();

		String sql = "select id,titulo,empresa_id from questionario order by id desc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Questionario questionario = null;

		while (rs.next()) {

			questionario = new Questionario();

			questionario.setId(rs.getLong("id"));
			questionario.setTitulo(rs.getString("titulo"));

			lista.add(questionario);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	public List<Participantes> listarParticipantesAvaliacoes(Long participantes_id) throws Exception {

		String sql = "select * from participantes_avaliacao where participantes_id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, participantes_id);

		ResultSet rs = stmt.executeQuery();

		List<Participantes> lista = new ArrayList<Participantes>();

		while (rs.next()) {

			Participantes participantes = new Participantes();

			participantes.setId(rs.getLong("id"));
			participantes.setNome(rs.getString("nome"));
			participantes.setEmail(rs.getString("email"));

			lista.add(participantes);

		}

		rs.close();
		stmt.close();
		con.close();

		return lista;
	}
	
	
	public List<Questionario> avaliacaoPDF(Long id) throws Exception {

		String sql = "SELECT "
						+ "r.id AS idResposta, "
						+ "qe.id AS idQuest, "
						+ "q.numeroquestao_id, "
						+ "r.resposta, "
						+ "q.questao, "
						+ "qe.titulo, "
						+ "s.id AS idsetor, "
						+ "s.descricao AS descricao, "
						+ "emp.nome as nomeEmpresa,emp.razao,emp.cnpj,emp.logo "
						+ "FROM "
						+ "respostas AS r "
						+ "INNER JOIN "
						+ "questao AS q ON (r.numeroquestao_id = q.numeroquestao_id) "
						+ "INNER JOIN "
						+ "questionario AS qe ON (qe.id = q.questionario_id) "
						+ "INNER JOIN "
						+ "setor AS s ON (s.id = q.setor_id) "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = qe.empresa_id) "
						+ "WHERE "
		                + "q.questionario_id = ? "
		                + "ORDER BY r.id";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		List<Questionario> lista = new ArrayList<Questionario>();

		while (rs.next()) {

			Questionario questionario = new Questionario();

			questionario.setId(rs.getLong("idQuest"));
			questionario.setTitulo(rs.getString("titulo"));
			Questao quest = new Questao();
			quest.setQuestao(rs.getString("questao"));
			quest.setQuestionario(questionario);
			Setor setor = new Setor();
			quest.setSetor(setor);
				
			Resposta res = new Resposta();
			res.setId(rs.getLong("idResposta"));
			res.setResposta(rs.getString("resposta"));
			
			Empresa emp = new Empresa();
			emp.setNome(rs.getString("nomeEmpresa"));
			emp.setRazao(rs.getString("razao"));
			emp.setCnpj(rs.getString("cnpj"));
			emp.setLogo(rs.getString("logo"));
			questionario.setEmpresa(emp);

			lista.add(questionario);

		}

		rs.close();
		stmt.close();
		con.close();

		return lista;
	}
	
	
	public List<Participantes> listarParticipantesPorAvaliacao2(Questionario questionario) throws Exception {
		return this.listarParticipantesAvaliacoes(questionario.getId());

	}
	
	public void adicionarParticipantesParaAvaliacao(Questionario questionario, List<Participantes> listarAlunos) throws SQLException {

		con = new ConnectionFactory().getConnection();

		removerParticipantesPorAvaliacao(questionario);

		for (Participantes participantes : listarAlunos) {
			incluirParticipantesPorAvaliacao(questionario, participantes);
		}

		con.close();

	}
	
	private void incluirParticipantesPorAvaliacao(Questionario questionario,Participantes  participantes) throws SQLException {

		String sql = "INSERT INTO participantes_avaliacao (participantes_id, questionario_id) " + "VALUES (?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, participantes.getId());
		stmt.setLong(2, questionario.getId());

		stmt.execute();
		stmt.close();

	}
	
	private void removerParticipantesPorAvaliacao(Questionario questionario) throws SQLException {

		String sql = "DELETE FROM participantes_avaliacao WHERE questionario_id=?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, questionario.getId());

		stmt.execute();
		stmt.close();

	}

	public void incluir(Questionario questionario) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO questionario (titulo,empresa_id) " + " VALUES (?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, questionario.getTitulo());
		stmt.setLong(2, questionario.getEmpresa().getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(Questionario questionario) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "update questionario set titulo=?,empresa_id=? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, questionario.getTitulo());
		stmt.setLong(2, questionario.getEmpresa().getId());
		stmt.setLong(3, questionario.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(Questionario questionario) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from questionario where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, questionario.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}
