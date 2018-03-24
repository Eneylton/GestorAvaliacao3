package com.java.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.java.conexao.ConnectionFactory;
import com.java.modelo.Empresa;
import com.java.modelo.Estatistica;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;

public class EstatisticaDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public List<Estatistica> listarTodos() throws SQLException {

		List<Estatistica> lista = new ArrayList<Estatistica>();

		String sql = "select res.id as idResposta, res.data, qe.id as idQuestao,qe.questao as quest, res.resposta, res.contador "
		           + "from questao as qe inner join respostas as res "
			       + "on (qe.numeroquestao_id = res.numeroquestao_id) limit 10";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Estatistica estatistica = null;

		while (rs.next()) {

			estatistica = new Estatistica();

			estatistica.setId(rs.getLong("idResposta"));
			estatistica.setDataInicio(rs.getTimestamp("data"));

			Questao quest = new Questao();
			quest.setId(rs.getLong("idQuestao"));
			quest.setQuestao(rs.getString("quest"));
			estatistica.setQuestao(quest);

			estatistica.setResposta(rs.getString("resposta"));

			estatistica.setContador(rs.getInt("contador"));

			lista.add(estatistica);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	public List<Estatistica> listarTodosPorQuestao(Long id, Long idQuest,Date dataInicio,Date dataFim) throws SQLException {

		List<Estatistica> lista = new ArrayList<Estatistica>();

		String sql = "select res.id as idResposta, res.data, qe.id as idQuestao,qe.questao as quest,q.id as idQuestionario, "
				+ "res.resposta, res.contador,emp.nome as nomeEmpresa,emp.razao,emp.cnpj,emp.logo " 
				+ "from questao as qe " 
				+ "inner join respostas as res on (qe.numeroquestao_id = res.numeroquestao_id) "
				+ "inner join questionario as q on (qe.questionario_id = q.id) "
                + "inner join empresa as emp on (emp.id = q.empresa_id) "
				+ "where res.questao_id = ? and qe.questionario_id = ? "
				+ "and res.data between ? and ?";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setLong(1, id);
		stmt.setLong(2, idQuest);
		stmt.setDate(3, new java.sql.Date(dataInicio.getTime()));
		stmt.setDate(4, new java.sql.Date(dataFim.getTime()));
		
		ResultSet rs = stmt.executeQuery();

		Estatistica estatistica = null;

		while (rs.next()) {

			estatistica = new Estatistica();

			estatistica.setId(rs.getLong("idResposta"));
			estatistica.setDataInicio(rs.getDate("data"));

			Questao quest = new Questao();
			quest.setId(rs.getLong("idQuestao"));
			quest.setQuestao(rs.getString("quest"));
			
			Questionario questionario = new Questionario();
		    questionario.setId(rs.getLong("idQuestionario"));
		    Empresa emp = new Empresa();
		    emp.setNome(rs.getString("nomeEmpresa"));
		    emp.setRazao(rs.getString("razao"));
		    emp.setCnpj(rs.getString("cnpj"));
		    emp.setLogo(rs.getString("logo"));
		    questionario.setEmpresa(emp);
			
			
			estatistica.setQuestao(quest);

			estatistica.setResposta(rs.getString("resposta"));

			estatistica.setContador(rs.getInt("contador"));

			lista.add(estatistica);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	
public List<Questao> buscarQuestaoporQuestionario(Long id) throws SQLException {
		
		List<Questao> lista = new ArrayList<Questao>();

		String sql = "select q.id,q.numeroquestao_id as numeroQuestao,q.questao from questao as q where q.questionario_id = ?";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);


		ResultSet rs = stmt.executeQuery();

		Questao questao = null;

		while (rs.next()) {

			questao = new Questao();

			questao.setId(rs.getLong("id"));
			questao.setNumeroQuestao_id(rs.getInt("numeroQuestao"));
			questao.setQuestao(rs.getString("questao"));

			lista.add(questao);

		}

		stmt.close();
		con.close();

		return lista;

	}


}
