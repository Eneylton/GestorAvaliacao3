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
import com.java.modelo.Usuario;

public class EmpresaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection con;

	public Empresa retornarEmpresaPorID(Long id) throws ClassNotFoundException, SQLException {

		String sql = "select al.id, al.nome,al.razao,al.nome,al.cnpj,al.logo "
				+ "from empresa as al "
				+ "where al.id = ?";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Empresa empresa = null;

		while (rs.next()) {

			empresa = new Empresa();

			empresa.setId(rs.getLong("id"));
			empresa.setRazao(rs.getString("razao"));
			empresa.setNome(rs.getString("nome"));
			empresa.setCnpj(rs.getString("cnpj"));
			empresa.setLogo(rs.getString("logo"));

		}

		stmt.close();
		con.close();

		return empresa;

	}

	public List<Empresa> listarTodos() throws SQLException {

		List<Empresa> lista = new ArrayList<Empresa>();

			String sql = "select al.id, al.nome,al.razao,al.cnpj,al.logo "
					+ "from empresa as al "
					+ "order by id desc ";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Empresa empresa = null;

		while (rs.next()) {

			empresa = new Empresa();

			empresa.setId(rs.getLong("id"));
			empresa.setNome(rs.getString("nome"));
			empresa.setRazao(rs.getString("razao"));
			empresa.setCnpj(rs.getString("cnpj"));
			empresa.setLogo(rs.getString("logo"));

			lista.add(empresa);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	
	public List<Empresa> listarUsuarioPorEmpresa() throws SQLException {

		List<Empresa> lista = new ArrayList<Empresa>();

			String sql = "SELECT "
							+ "aut.nome, "
							+ "count(us.id) as total "
							+ "FROM "
							+ "empresa AS aut "
							+ "INNER JOIN "
							+ "usuario us ON (aut.id = us.empresa_id) group by aut.nome";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		Empresa empresa = null;

		while (rs.next()) {

			empresa = new Empresa();

			
			empresa.setNome(rs.getString("nome"));
			empresa.setTotal(rs.getInt("total"));
			
			lista.add(empresa);

		}

		stmt.close();
		con.close();

		return lista;

	}
	
	
	
	public List<Empresa> listarUsuariosPorEmpresa(Long id) throws SQLException {

		List<Empresa> lista = new ArrayList<Empresa>();

			String sql = "SELECT "
							+ "aut.id, "
							+ "aut.nome AS nomeEmpresa, "
							+ "aut.logo, "
							+ "aut.razao, "
							+ "aut.cnpj, "
							+ "us.id AS idUsuario, "
							+ "us.nomeCompleto, "
							+ "us.login "
							+ "FROM "
							+ "empresa AS aut "
							+ "INNER JOIN "
							+ "usuario us ON (aut.id = us.Empresa_id) "
							+ "WHERE "
							+ "us.empresa_id = ?";

		con = new ConnectionFactory().getConnection();

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);

		ResultSet rs = stmt.executeQuery();

		Empresa empresa = null;

		while (rs.next()) {

			empresa = new Empresa();

			empresa.setId(rs.getLong("id"));
			empresa.setNome(rs.getString("nomeEmpresa"));
			empresa.setRazao(rs.getString("razao"));
			empresa.setCnpj(rs.getString("cnpj"));
			empresa.setLogo(rs.getString("logo"));
			
			Usuario usu = new Usuario();
			usu.setId(rs.getLong("idUsuario"));
			usu.setLogin(rs.getString("login"));
			usu.setNomeCompleto(rs.getString("nomeCompleto"));
			
			empresa.setUsuario(usu);
			
			lista.add(empresa);

		}

		stmt.close();
		con.close();

		return lista;

	}

	

	public void incluir(Empresa empresa) throws SQLException {

		con = new ConnectionFactory().getConnection();

		/*
		 * SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); String
		 * dataFormatada = dt1.format(Categoria.getData());
		 */

		String sql = "INSERT INTO empresa (razao,nome,cnpj,logo) " + " VALUES (?,?,?,?)";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, empresa.getRazao());
		stmt.setString(2, empresa.getNome());
		stmt.setString(3, empresa.getCnpj());
		stmt.setString(4, empresa.getLogo());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void alterar(Empresa empresa) throws SQLException {

		con = new ConnectionFactory().getConnection();

		/*
		 * SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); String
		 * dataFormatada = dt1.format(Categoria.getData());
		 */

		String sql = "update empresa set " + "razao=?,nome=?,cnpj=?,logo=? where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setString(1, empresa.getRazao());
		stmt.setString(2, empresa.getNome());
		stmt.setString(3, empresa.getCnpj());
		stmt.setString(4, empresa.getLogo());

		stmt.setLong(5, empresa.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

	public void excluir(Empresa empresa) throws SQLException {

		con = new ConnectionFactory().getConnection();

		String sql = "delete from empresa where id = ?";

		PreparedStatement stmt;

		stmt = con.prepareStatement(sql);

		stmt.setLong(1, empresa.getId());

		stmt.execute();
		stmt.close();
		con.close();

	}

}