package com.java.controller.participantes;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.modelo.AvaliacaoParticipantes;
import com.java.modelo.Participantes;
import com.java.modelo.Questionario;
import com.java.service.ParticipantesService;
import com.java.util.FacesMessages;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ParticipantesQuestionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ParticipantesService participantesService;

	@Inject
	private FacesMessages messages;

	private List<Participantes> listarParticipantess;

	private List<Participantes> pesquisaParticipantess;

	private List<Questionario> listaParticipantesQuest;
	
	private Questionario questionario;

	private Participantes participantes;

	private AvaliacaoParticipantes avaliacaoParticipantes;

	private Participantes participantesEdicao = new Participantes();

	private Participantes participantesSelecionada;

	@PostConstruct
	public void init() {

		try {

			this.limpar();

			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String idQuestao = params.get("questionario");

			listaParticipantesQuest = participantesService.listarTodosParticipantesPorQuestionario(Long.parseLong(idQuestao));
			

					
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no Processamento - Erro: " + ex.getMessage());
		}

	}

	public void prepararNovoCadastro() {
		participantesEdicao = new Participantes();
	}

	public void salvar() throws SQLException {
		try {

			if (participantesEdicao.getId() == null) {
				participantesService.incluir(participantesEdicao);
				consultar();
				pesquisar();

				messages.info("Registro Salvo com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:participantesTable"));

			} else {

				participantesService.alterar(participantesEdicao);
				consultar();
				pesquisar();

				messages.info("Registro Alterado com sucesso!");

				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:participantesTable"));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() throws SQLException {
		participantesService.excluir(participantesSelecionada);
		participantesSelecionada = null;

		consultar();
		limpar();

		messages.info("Participantes excluído com sucesso!");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:participantesTable"));
	}

	public void consultar() throws SQLException {

		listarParticipantess = participantesService.listarTodos();

	}

	public void pesquisar() throws SQLException {

		pesquisaParticipantess = participantesService.listarTodos();

	}

	public void limpar() {
		this.participantes = new Participantes();
	}

	public FacesMessages getMessages() {
		return messages;
	}

	public void setMessages(FacesMessages messages) {
		this.messages = messages;
	}

	public List<Participantes> getListarParticipantess() {
		return listarParticipantess;
	}

	public void setListarParticipantess(List<Participantes> listarParticipantess) {
		this.listarParticipantess = listarParticipantess;
	}

	public List<Participantes> getPesquisaParticipantess() {
		return pesquisaParticipantess;
	}

	public void setPesquisaParticipantess(List<Participantes> pesquisaParticipantess) {
		this.pesquisaParticipantess = pesquisaParticipantess;
	}

	public Participantes getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Participantes Participantes) {
		this.participantes = Participantes;
	}

	public Participantes getParticipantesEdicao() {
		return participantesEdicao;
	}

	public void setParticipantesEdicao(Participantes ParticipantesEdicao) {
		this.participantesEdicao = ParticipantesEdicao;
	}

	public Participantes getParticipantesSelecionada() {
		return participantesSelecionada;
	}

	public void setParticipantesSelecionada(Participantes ParticipantesSelecionada) {
		this.participantesSelecionada = ParticipantesSelecionada;
	}

	public List<Questionario> getListaParticipantesQuest() {
		return listaParticipantesQuest;
	}

	public void setListaParticipantesQuest(List<Questionario> listaParticipantesQuest) {
		this.listaParticipantesQuest = listaParticipantesQuest;
	}

	public AvaliacaoParticipantes getAvaliacaoParticipantes() {
		return avaliacaoParticipantes;
	}

	public void setAvaliacaoParticipantes(AvaliacaoParticipantes avaliacaoParticipantes) {
		this.avaliacaoParticipantes = avaliacaoParticipantes;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

}
