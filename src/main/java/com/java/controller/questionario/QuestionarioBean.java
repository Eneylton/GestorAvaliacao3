package com.java.controller.questionario;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.dao.EmpresaDAO;
import com.java.modelo.Questionario;
import com.java.service.QuestionarioService;
import com.java.service.RelatorioService;
import com.java.util.FacesMessages;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class QuestionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaDAO empresaDAO;

	@Inject
	private QuestionarioService questionarioService;

	@Inject
	private FacesMessages messages;
	
	@Inject
	private RelatorioService relatorioService;

	private List<Questionario> listarQuestionarios;

	private List<Questionario> pesquisaQuestionarios;

	private Questionario questionario;

	private Questionario questionarioEdicao = new Questionario();

	private Questionario questionarioSelecionada;

	@PostConstruct
	public void init() {

		try {

			consultar();
			pesquisar();
			this.limpar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no Processamento - Erro: " + ex.getMessage());
		}

	}

	public void prepararNovoCadastro() {
		questionarioEdicao = new Questionario();
	}

	public void salvar() throws SQLException {
		try {

			if (questionarioEdicao.getId() == null) {

				questionarioEdicao.setEmpresa(empresaDAO.retornarEmpresaPorID(Session.retornaIdEmpresa()));

				questionarioService.incluir(questionarioEdicao);
				consultar();
				pesquisar();

				messages.info("Questionario salva com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:questionarioTable"));

			} else {

				questionarioEdicao.setEmpresa(empresaDAO.retornarEmpresaPorID(Session.retornaIdEmpresa()));

				questionarioService.alterar(questionarioEdicao);
				consultar();
				pesquisar();

				messages.info("Registro Alterado com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:questionarioTable"));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() throws SQLException {
		questionarioService.excluir(questionarioSelecionada);
		questionarioSelecionada = null;

		consultar();
		limpar();

		messages.info("Questionario excluído com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:questionarioTable"));
	}

	public void consultar() throws SQLException {
		
		Long idEmpresa = Session.retornaIdEmpresa();

		listarQuestionarios = questionarioService.listarTodosPorEmpresas(idEmpresa);

	}

	public void pesquisar() throws SQLException {
		Long idEmpresa = Session.retornaIdEmpresa();

		pesquisaQuestionarios = questionarioService.listarTodosPorEmpresas(idEmpresa);

	}
	
	public void avaliacaoPDF() throws Exception{
		
		Long idQuestionario = questionarioSelecionada.getId();
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("questionarioID", idQuestionario);
		
		listarQuestionarios = questionarioService.avaliacaoPDF(idQuestionario);
		relatorioService.gerarAvaliacaoPDF(listarQuestionarios, (HashMap<String, Object>) parametros, "Avaliacao2");
		
		
	}

	public void limpar() {
		this.questionario = new Questionario();
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public List<Questionario> getListarQuestionarios() {
		return listarQuestionarios;
	}

	public void setListarQuestionarios(List<Questionario> listarQuestionarios) {
		this.listarQuestionarios = listarQuestionarios;
	}

	public List<Questionario> getPesquisaQuestionarios() {
		return pesquisaQuestionarios;
	}

	public void setPesquisaQuestionarios(List<Questionario> pesquisaQuestionarios) {
		this.pesquisaQuestionarios = pesquisaQuestionarios;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public Questionario getQuestionarioEdicao() {
		return questionarioEdicao;
	}

	public void setQuestionarioEdicao(Questionario questionarioEdicao) {
		this.questionarioEdicao = questionarioEdicao;
	}

	public Questionario getQuestionarioSelecionada() {
		return questionarioSelecionada;
	}

	public void setQuestionarioSelecionada(Questionario questionarioSelecionada) {
		this.questionarioSelecionada = questionarioSelecionada;
	}

}
