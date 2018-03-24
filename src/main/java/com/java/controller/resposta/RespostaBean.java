package com.java.controller.resposta;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.dao.RespostaDAO;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;
import com.java.modelo.Resposta;
import com.java.service.QuestaoService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RespostaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RespostaDAO respostaDAO;

	@Inject
	private QuestaoService questaoService;

	private List<Resposta> listaRespostas = new ArrayList<>();

	private Resposta resposta = new Resposta();

	private Questao questao;

	private Questionario questionario = new Questionario();

	private String pergunta;

	@PostConstruct
	public void init() {

		try {

			this.limpar();

			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String idResposta = params.get("questao");
			if (idResposta == null || idResposta.isEmpty()) {

			} else {

				questao = questaoService.retornarQuestaoPorID(Long.parseLong(idResposta));

				int idNquest = questao.getNumeroQuestao_id();
				
				Long idQuestao = questao.getId();
				
				this.resposta.setQuestaoID(Integer.valueOf(idQuestao.toString()));
				
				this.resposta.setNumeroQuestao(idNquest);
				Long idQuestiaonario = questao.getQuestionario().getId();
				
				this.resposta.setQuestionario(Integer.valueOf(idQuestiaonario.toString()));
				
				
			
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public void adicionar() throws ClassNotFoundException, SQLException {
		Resposta quest = new Resposta();
		
		quest.setResposta(resposta.getPergunta());
		int idNquest = questao.getNumeroQuestao_id();
		quest.setNumeroQuestao(idNquest);
		Long idQuestiaonario = questao.getQuestionario().getId();
		Long idQuestao = questao.getId();
		quest.setQuestionario(Integer.valueOf(idQuestiaonario.toString()));
		quest.setQuestaoID(Integer.valueOf(idQuestao.toString()));

		if (listaRespostas.size() <= 10) {
			listaRespostas.add(quest);
			FacesUtil.addSuccessMessage("Resposta Adicionada com Sucesso !!!");

		} else {
			FacesUtil.addErrorMessage("Exedeu o Número Máximo de Respostas: " + listaRespostas.size());
		}

	}

	public void remover() {

		pergunta = resposta.getResposta();

		for (int i = 0; i < listaRespostas.size(); i++) {
			if (listaRespostas.get(i).getResposta().equals(pergunta)) {
				listaRespostas.remove(i);
				FacesUtil.addErrorMessage("Questão Removida !!! ");

			}
		}
	}

	public void salvar() {

		try {

			if (resposta.getId() == null) {
				respostaDAO.registrarLista(listaRespostas);
			} else {
				respostaDAO.registrarLista(listaRespostas);
			}

			FacesUtil.addSuccessMessage("Resposta salvo com sucesso!");

			this.limpar();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.resposta = new Resposta();
	}

	public List<Resposta> getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(List<Resposta> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

}
