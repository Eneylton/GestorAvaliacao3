package com.java.controller.questao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.java.dao.QuestaoDAO;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;
import com.java.modelo.Setor;
import com.java.service.QuestionarioService;
import com.java.service.SetorService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class QuestaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private QuestaoDAO questaoDAO;

	@Inject
	private QuestionarioService questionarioService;

	private List<Questao> listaQuestoes = new ArrayList<>();

	@Inject
	private SetorService setorService;

	private Map<String, Setor> listarSetor = new HashMap<String, Setor>();

	private Questao questao = new Questao();

	private Questionario questionario = new Questionario();

	private String pergunta;

	@PostConstruct
	public void init() {

		try {

			this.limpar();
			preencheListasSelect();

			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String idQuestao = params.get("questionario");
			if (idQuestao == null || idQuestao.isEmpty()) {

			} else {
				this.questao.setQuestionario(questionarioService.retornarQuestionarioPorID(Long.parseLong(idQuestao)));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public void adicionar() throws ClassNotFoundException, SQLException {
		Questao quest = new Questao();
		Long idQuestao = questao.getQuestionario().getId();
		Long idSetor = questao.getSetor().getId();

		int i = listaQuestoes.size() + 1;

		quest.setNumeroQuestao(i);
		quest.setQuestao(questao.getPergunta());
		quest.setQuestionario(questionarioService.retornarQuestionarioPorID(idQuestao));
		quest.setSetor(setorService.retornarSetorPorID(idSetor));
		quest.setCheckin(questao.getCheckin());
		quest.setSubjetivas(questao.getSubjetivas());

		if (listaQuestoes.size() <= 10) {
			listaQuestoes.add(quest);
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:editor"));

		} else {
			FacesUtil.addErrorMessage("Exedeu o Número Máximo de Questões: " + listaQuestoes.size());
		}

	}

	public void remover() {

		pergunta = questao.getQuestao();

		for (int i = 0; i < listaQuestoes.size(); i++) {
			if (listaQuestoes.get(i).getQuestao().equals(pergunta)) {
				listaQuestoes.remove(i);
				FacesUtil.addErrorMessage("Questão Removida !!! ");

			}
		}
	}

	public void salvar() {

		try {

			if (questao.getId() == null) {
				questaoDAO.registrarLista(listaQuestoes);
			} else {
				questaoDAO.registrarLista(listaQuestoes);
			}
			
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:editor", "frm:table"));

			FacesUtil.addSuccessMessage("Registro salvo com sucesso!");

			this.limpar();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private void preencheListasSelect() throws SQLException {

		listarSetor = new HashMap<String, Setor>();
		for (Setor setor : setorService.listarTodos()) {
			listarSetor.put(setor.getDescricao(), setor);
		}

	}

	public void limpar() {
		this.questao = new Questao();
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public List<Questao> getListaQuestoes() {
		return listaQuestoes;
	}

	public void setListaQuestoes(List<Questao> listaQuestoes) {
		this.listaQuestoes = listaQuestoes;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public Map<String, Setor> getListarSetor() {
		return listarSetor;
	}

	public void setListarSetor(Map<String, Setor> listarSetor) {
		this.listarSetor = listarSetor;
	}

}
