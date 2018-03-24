package com.java.controller.avaliacaoParticipantes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.EmailException;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.java.modelo.Participantes;
import com.java.modelo.Questionario;
import com.java.service.ParticipantesService;
import com.java.service.QuestionarioService;
import com.java.util.EmailUtil;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class listaEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private QuestionarioService questionarioService;

	@Inject
	private ParticipantesService participantesService;

	private List<Participantes> listarParticpantes = new ArrayList<>();

	private List<Participantes> participantesSelecionados = new ArrayList<>();

	private Participantes participantesSelecionado;

	private Questionario questionario;

	@PostConstruct
	public void init() {

		try {

			listarParticpantes = participantesService.listarTodos();

			for (Participantes part : listarParticpantes) {
				part.setSelecionado(false);
			}

			String idquestionario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("questionario");

			Questionario questionarioSelecionado = questionarioService
					.retornarQuestionarioPorID(Long.parseLong(idquestionario));

			for (Participantes part : questionarioSelecionado.getParticipantes()) {
				this.adicionarParticipantesSelecionado(part);

				for (int i = 0; i < listarParticpantes.size(); i++) {

					if (listarParticpantes.get(i).getId() == part.getId()) {
						listarParticpantes.get(i).setSelecionado(true);
						
						
						
						break;
					}
				}

			}

			this.limpar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public void salvar() {

		try {

			questionarioService.adicionarParticipantesParaAvaliacao(this.questionario, participantesSelecionados);
				

			FacesUtil.addSuccessMessage("Registro salvo com sucesso!");

			this.limpar();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private void adicionarParticipantesSelecionado(Participantes participantes) throws EmailException {
		
		
		
		this.participantesSelecionados.add(participantes);
		
		
		EmailUtil.enviarEmail("eliasbarrospai2017@gmail.com", 
				participantes.getEmail(), retornaMensagem(participantes));
		
	}

	private void removerAcessorioSelecionado(Participantes participantes) {
		this.participantesSelecionados.remove(participantes);
	}

	public void onRowSelect(SelectEvent event) throws EmailException {
		System.out.println("Select Event");
		adicionarParticipantesSelecionado(((Participantes) event.getObject()));
	}

	public void onRowUnSelect(UnselectEvent event) {
		System.out.println("UnSelect Event");
		removerAcessorioSelecionado(((Participantes) event.getObject()));
	}
	
	
public static String retornaMensagem(Participantes participantes) {
		
		StringBuffer msgs = new StringBuffer();
	
		msgs.append("BRAVOS - DESENVOLVIMENTO DE SISTEMAS LTDA. ");
		msgs.append("<br />");
		
		msgs.append("Prezado(a)  <b>"
				+ participantes.getNome()
				+ "</b>, Conforme solicitado, seu cadastro foi efetuado com sucesso."
				+ "Clique no link: "
				+ " http://localhost:8080/GestorAvaliacao3.0/Avaliacao/Avaliacao.xhtml?participantes="+ participantes.getId());
		
		
		return msgs.toString();
		
	}
	
	

	public void limpar() {
		this.questionario = new Questionario();
	}

	public List<Participantes> getListarParticpantes() {
		return listarParticpantes;
	}

	public void setListarParticpantes(List<Participantes> listarParticpantes) {
		this.listarParticpantes = listarParticpantes;
	}

	public List<Participantes> getParticipantesSelecionados() {
		return participantesSelecionados;
	}

	public void setParticipantesSelecionados(List<Participantes> participantesSelecionados) {
		this.participantesSelecionados = participantesSelecionados;
	}

	public Participantes getParticipantesSelecionado() {
		return participantesSelecionado;
	}

	public void setParticipantesSelecionado(Participantes participantesSelecionado) {
		this.participantesSelecionado = participantesSelecionado;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

}
