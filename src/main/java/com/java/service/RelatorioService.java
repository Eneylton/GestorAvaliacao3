package com.java.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.java.modelo.Estatistica;
import com.java.modelo.Questionario;
import com.java.util.Constantes;
import com.java.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class RelatorioService implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private HttpServletResponse response;
	private FacesContext context;
	private Connection con;

	public RelatorioService() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}

	public void gerarGaficoPDF(List<Estatistica> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {
		
		try {

		String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
				+ ".jrxml";


		JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

		JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

		response.setHeader("Content-disposition", "inline; filename=Estatistica.pdf");
		
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(print, stream);
		
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();

			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}
	
	
	public void gerarAvaliacaoPDF(List<Questionario> consulta, HashMap<String, Object> parametros, String relatorio)
			throws JRException {
		
		try {

		String caminhoFisicoReport = Constantes.CAMINHO_FISICO_BASE_WEBAPP + "resources/relatorios/" + relatorio
				+ ".jrxml";


		JasperReport report = JasperCompileManager.compileReport(caminhoFisicoReport);

		JasperPrint print = JasperFillManager.fillReport(report, parametros, getConexao());

		response.setHeader("Content-disposition", "inline; filename=Estatistica.pdf");
		
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(print, stream);
		
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();

	



			closeConnection();

		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + e.getMessage());
		}

	}
	
	
	private Connection getConexao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/db_satisfacao?autoReconnect=true", "root",
					"ti1234");
			return con;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
