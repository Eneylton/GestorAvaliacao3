package com.java.controller.setor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.Setor;
import com.java.service.SetorService;
import com.java.util.FacesMessages;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SetorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SetorService setorService;

	@Inject
	private FacesMessages messages;

	private List<Setor> listarSetors;

	private List<Setor> pesquisaSetors;

	private Setor setor;

	private Setor setorEdicao = new Setor();

	private Setor setorSelecionada;

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
		setorEdicao = new Setor();
	}

	public void salvar() throws SQLException {
		try {

			if (setorEdicao.getId() == null) {

				setorService.incluir(setorEdicao);
				consultar();
				pesquisar();

				messages.info("Setor salva com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:setorTable"));

			} else {

				setorService.alterar(setorEdicao);
				consultar();
				pesquisar();

				messages.info("Registro Alterado com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:setorTable"));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() throws SQLException {
		setorService.excluir(setorSelecionada);
		setorSelecionada = null;

		consultar();
		limpar();

		messages.info("Setor excluído com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:setorTable"));
	}

	public void consultar() throws SQLException {

		listarSetors = setorService.listarTodos();

	}

	public void pesquisar() throws SQLException {

		pesquisaSetors = setorService.listarTodos();

	}

	public void limpar() {
		this.setor = new Setor();
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public List<Setor> getListarSetors() {
		return listarSetors;
	}

	public void setListarSetors(List<Setor> listarSetors) {
		this.listarSetors = listarSetors;
	}

	public List<Setor> getPesquisaSetors() {
		return pesquisaSetors;
	}

	public void setPesquisaSetors(List<Setor> pesquisaSetors) {
		this.pesquisaSetors = pesquisaSetors;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Setor getSetorEdicao() {
		return setorEdicao;
	}

	public void setSetorEdicao(Setor setorEdicao) {
		this.setorEdicao = setorEdicao;
	}

	public Setor getSetorSelecionada() {
		return setorSelecionada;
	}

	public void setSetorSelecionada(Setor setorSelecionada) {
		this.setorSelecionada = setorSelecionada;
	}

}
