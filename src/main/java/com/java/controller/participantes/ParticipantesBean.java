package com.java.controller.participantes;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.dao.EmpresaDAO;
import com.java.modelo.Participantes;
import com.java.service.ParticipantesService;
import com.java.util.FacesMessages;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ParticipantesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ParticipantesService participantesService;

	@Inject
	private EmpresaDAO empresaDAO;

	@Inject
	private FacesMessages messages;

	private List<Participantes> listarParticipantes;

	private List<Participantes> pesquisaParticipantes;

	private Participantes participantes;

	private Participantes participantesEdicao = new Participantes();

	private Participantes participantesSelecionada;

	@PostConstruct
	public void init() {

		try {

			consultar();
			pesquisar();

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

				Long empresaID = Session.retornaIdEmpresa();

				participantesEdicao.setEmpresa(empresaDAO.retornarEmpresaPorID(empresaID));
				participantesService.incluir(participantesEdicao);

				consultar();
				pesquisar();

				messages.info("Registro Salvo e Email enviado com sucesso!");

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

		Long idEmpresa = Session.retornaIdEmpresa();

		listarParticipantes = participantesService.listarTodosPorEmpresa(idEmpresa);

	}

	public void pesquisar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();

		pesquisaParticipantes = participantesService.listarTodosPorEmpresa(idEmpresa);

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

	public List<Participantes> getListarParticipantes() {
		return listarParticipantes;
	}

	public void setListarParticipantes(List<Participantes> listarParticipantes) {
		this.listarParticipantes = listarParticipantes;
	}

	public List<Participantes> getPesquisaParticipantes() {
		return pesquisaParticipantes;
	}

	public void setPesquisaParticipantes(List<Participantes> pesquisaParticipantes) {
		this.pesquisaParticipantes = pesquisaParticipantes;
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

}
