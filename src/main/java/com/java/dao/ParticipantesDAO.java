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
import com.java.modelo.Empresa;
import com.java.modelo.Participantes;
import com.java.modelo.Questionario;

public class ParticipantesDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public Participantes retornarParticipantesPorID(Long id) throws ClassNotFoundException, SQLException {

				String sql = "SELECT "
						+ "p.id, p.nome, p.email, p.questoesRespondidas,qe.id as idQuest,p.contadorID,"
						+ "emp.id as empresaID,emp.nome as empresa "
						+ "FROM "
						+ "participantes AS p "
						+ "INNER JOIN "
						+ "participantes_avaliacao AS pa ON (pa.participantes_id = p.id) "
						+ "inner join questionario as qe ON (pa.questionario_id = qe.id) "
						+ "inner join empresa emp ON (emp.id = p.empresa_id) "
						+ "WHERE p.id = ?";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Participantes participantes = null;

		while (rs.next()) {

			participantes = new Participantes();

			participantes.setId(rs.getLong("id"));
			participantes.setNome(rs.getString("nome"));
			participantes.setEmail(rs.getString("email"));
			participantes.setQuestoesRespondidas(rs.getInt("questoesRespondidas"));
			participantes.setContadorID(rs.getInt("contadorID"));
			
			Empresa emp = new Empresa();
			emp.setId(rs.getLong("empresaID"));
			emp.setNome(rs.getString("empresa"));
			participantes.setEmpresa(emp);
			
			
			AvaliacaoParticipantes ap = new AvaliacaoParticipantes();
			
			Questionario quest = new Questionario();
			
			quest.setId(rs.getLong("idQuest"));
			ap.setQuestionario(quest);
			participantes.setAvaliacaoParticipantes(ap);
			

		}

		stmt.close();
		con.close();

		return participantes;

	}

	public List<Participantes> listarTodos() throws SQLException {

		List<Participantes> lista = new ArrayList<Participantes>();

				String sql = "SELECT "
						+ "p.id, p.nome, p.email, p.questoesRespondidas, p.contadorID,emp.id as empresaID, " 
						+ "emp.nome as empresa "
						+ "FROM "
						+ "participantes AS p "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = p.empresa_id) "
						+ "ORDER BY id DESC ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Participantes participantes = null;

		while (rs.next()) {

			participantes = new Participantes();

			participantes.setId(rs.getLong("id"));
			participantes.setNome(rs.getString("nome"));
			participantes.setEmail(rs.getString("email"));
			participantes.setQuestoesRespondidas(rs.getInt("questoesRespondidas"));
			participantes.setContadorID(rs.getInt("contadorID"));

			lista.add(participantes);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	public List<Participantes> listarTodosPorEmpresa(Long id) throws SQLException {

		List<Participantes> lista = new ArrayList<Participantes>();

				String sql = "SELECT "
						+ "p.id, p.nome, p.email, p.questoesRespondidas, p.contadorID,emp.id as empresaID, " 
						+ "emp.nome as empresa "
						+ "FROM "
						+ "participantes AS p "
						+ "INNER JOIN "
						+ "empresa AS emp ON (emp.id = p.empresa_id) "
						+ "WHERE emp.id = ? "
						+ "ORDER BY id DESC ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Participantes participantes = null;

		while (rs.next()) {

			participantes = new Participantes();

			participantes.setId(rs.getLong("id"));
			participantes.setNome(rs.getString("nome"));
			participantes.setEmail(rs.getString("email"));
			participantes.setQuestoesRespondidas(rs.getInt("questoesRespondidas"));
			participantes.setContadorID(rs.getInt("contadorID"));

			lista.add(participantes);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	
	public List<Questionario> listarTodosParticipantesPorQuestionario(Long id) throws SQLException {

		List<Questionario> lista = new ArrayList<Questionario>();

		String sql = "SELECT qe.id, "
						+"qe.data,qe.titulo,p.id as idParticipantes, p.nome,p.email,pa.id as idAp "
						+"FROM "
						+"questionario AS qe "
						+"INNER JOIN "
						+"participantes_avaliacao pa ON (qe.id = pa.questionario_id) "
						+"inner join participantes as p ON (p.id = pa.participantes_id) WHERE qe.id = ? ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Questionario questionario = null;

		while (rs.next()) {

			questionario = new Questionario();

			questionario.setId(rs.getLong("id"));
			questionario.setTitulo(rs.getString("titulo"));
			questionario.setData(rs.getTimestamp("data"));
			
            AvaliacaoParticipantes ap = new AvaliacaoParticipantes();
            
            ap.setId(rs.getLong("idAp"));
            
            Participantes part = new Participantes();
            part.setId(rs.getLong("idParticipantes"));
			part.setNome(rs.getString("nome"));
			part.setEmail(rs.getString("email"));
            
            ap.setParticipantes(part);
            
            questionario.setAvaliacaoParticipantes(ap);
            
			lista.add(questionario);

		}

		stmt.close();
		con.close();

		return lista;

	}

	public void incluir(Participantes participantes) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO participantes (nome,email,questoesRespondidas,contadorID,empresa_id) " + " VALUES (?,?,?,?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, participantes.getNome());
		stmt.setString(2, participantes.getEmail());
		stmt.setInt(3, participantes.getQuestoesRespondidas());
		stmt.setInt(4, participantes.getContadorID());
		stmt.setLong(5, participantes.getEmpresa().getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(Participantes participantes) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "update participantes set nome=?,email=?,questoesRespondidas=?,contadorID=? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, participantes.getNome());
		stmt.setString(2, participantes.getEmail());
		stmt.setInt(3, participantes.getQuestoesRespondidas());
		stmt.setInt(4, participantes.getContadorID());
		stmt.setLong(5, participantes.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(Participantes participantes) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from participantes where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, participantes.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}
