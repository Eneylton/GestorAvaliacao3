package com.java.controller.questao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Questao;
import com.java.modelo.Resposta;
import com.java.service.QuestaoService;
import com.java.util.FacesMessages;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisarQuestaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private QuestaoService questaoService;

	@Inject
	private FacesMessages messages;

	private List<Questao> listarQuestoes;

	private List<Questao> pesquisaQuestoes;

	private List<Resposta> listaRespostas;

	private Questao questao;

	private Questao questaoEdicao = new Questao();

	private Questao questaoSelecionada;

	@PostConstruct
	public void init() {

		try {

			this.limpar();
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String idQuestao = params.get("questionario");
			
			listarQuestoes = questaoService.listarTodosPorQuestionario(Long.parseLong(idQuestao));
			pesquisaQuestoes = questaoService.listarTodosPorQuestionario(Long.parseLong(idQuestao));

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no Processamento - Erro: " + ex.getMessage());
		}

	}

	public void prepararNovoCadastro() {
		questaoEdicao = new Questao();
	}

	

	public void buscarRespostas() throws SQLException {
		
		int idQuest = questaoSelecionada.getNumeroQuestao_id();
		
		Long idQuestao = questaoSelecionada.getId();

		Long idResp = questaoSelecionada.getQuestionario().getId();

		listaRespostas = questaoService.buscarRespostasRetorno(idResp,idQuestao,idQuest);
		
		

	}

	public void limpar() {
		this.questao = new Questao();
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public List<Questao> getListarQuestaos() {
		return listarQuestoes;
	}

	public void setListarQuestaos(List<Questao> listarQuestaos) {
		this.listarQuestoes = listarQuestaos;
	}

	public List<Questao> getPesquisaQuestoes() {
		return pesquisaQuestoes;
	}

	public void setPesquisaQuestoes(List<Questao> pesquisaQuestoes) {
		this.pesquisaQuestoes = pesquisaQuestoes;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Questao getQuestaoEdicao() {
		return questaoEdicao;
	}

	public void setQuestaoEdicao(Questao questaoEdicao) {
		this.questaoEdicao = questaoEdicao;
	}

	public Questao getQuestaoSelecionada() {
		return questaoSelecionada;
	}

	public void setQuestaoSelecionada(Questao questaoSelecionada) {
		this.questaoSelecionada = questaoSelecionada;
	}

	public List<Questao> getListarQuestoes() {
		return listarQuestoes;
	}

	public void setListarQuestoes(List<Questao> listarQuestoes) {
		this.listarQuestoes = listarQuestoes;
	}

	public List<Resposta> getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(List<Resposta> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}

}