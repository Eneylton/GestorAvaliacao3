package com.java.controller.empresa;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.java.modelo.Empresa;
import com.java.service.EmpresaService;
import com.java.util.FacesMessages;
import com.java.util.FileUploadCopiarArquivo;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmpresaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaService EmpresaService;

	@Inject
	private FacesMessages messages;

	private UploadedFile arquivoUP;

	private List<Empresa> listarEmpresas;

	private List<Empresa> pesquisaEmpresas;

	private Empresa empresa;

	private Empresa empresaEdicao = new Empresa();

	private Empresa empresaSelecionada;

	@PostConstruct
	public void init() {

		try {

			this.limpar();

			consultar();
			pesquisar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no Processamento - Erro: " + ex.getMessage());
		}

	}

	public void prepararNovoCadastro() {
		empresaEdicao = new Empresa();
	}

	public void salvar() throws SQLException {
		try {

			String nomeArquivo = "";
			if (arquivoUP != null) {
				FileUploadCopiarArquivo.iniciarCopia(arquivoUP);
				nomeArquivo = arquivoUP.getFileName();
				empresaEdicao.setLogo(nomeArquivo);
			} else {
				empresaEdicao.setLogo("");
			}

			if (empresaEdicao.getId() == null) {

				EmpresaService.incluir(empresaEdicao);
				consultar();
				pesquisar();

				messages.info("Auto Escola Cadastrada com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:empresaTable"));

			} else {

				EmpresaService.alterar(empresaEdicao);
				consultar();
				pesquisar();

				messages.info("Registro Alterado com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:empresaTable"));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() throws SQLException {
		EmpresaService.excluir(empresaSelecionada);
		empresaSelecionada = null;

		consultar();
		limpar();

		messages.info("Auto Escola excluída com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:empresaTable"));
	}

	public void consultar() throws SQLException {

		listarEmpresas = EmpresaService.listarTodos();

	}

	public void pesquisar() throws SQLException {

		listarEmpresas = EmpresaService.listarTodos();

	}

	public void handleFileUpload(FileUploadEvent event) {
		this.setArquivoUP(event.getFile());
	}

	public void limpar() {
		this.empresa = new Empresa();
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public List<Empresa> getListarEmpresas() {
		return listarEmpresas;
	}

	public void setListarEmpresas(List<Empresa> listarEmpresas) {
		this.listarEmpresas = listarEmpresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa Empresa) {
		this.empresa = Empresa;
	}

	public Empresa getEmpresaEdicao() {
		return empresaEdicao;
	}

	public void setEmpresaEdicao(Empresa EmpresaEdicao) {
		this.empresaEdicao = EmpresaEdicao;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa EmpresaSelecionada) {
		this.empresaSelecionada = EmpresaSelecionada;
	}

	public UploadedFile getArquivoUP() {
		return arquivoUP;
	}

	public void setArquivoUP(UploadedFile arquivoUP) {
		this.arquivoUP = arquivoUP;
	}

	public List<Empresa> getPesquisaEmpresas() {
		return pesquisaEmpresas;
	}

	public void setPesquisaEmpresas(List<Empresa> pesquisaEmpresas) {
		this.pesquisaEmpresas = pesquisaEmpresas;
	}

}
