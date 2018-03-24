package com.java.controller.avaliacao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Participantes;
import com.java.modelo.Questao;
import com.java.modelo.Resposta;
import com.java.modelo.RespostaAberta;
import com.java.service.AvaliacaoService;
import com.java.service.ParticipantesService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AvaliacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AvaliacaoService avaliacaoService;

	@Inject
	private ParticipantesService participanteService;

	@SuppressWarnings({ "rawtypes" })

	private List questaoERespostas;

	private List<Resposta> respostas;

	private Participantes participantes;

	private int idRespostaSelect;

	private String[] idRespostasSelected;

	private String[] respostasSelected;

	private String respostaRespostaSelect;

	private Resposta respostaSelected;

	private Questao questao;

	private String tituloGrupo;

	private boolean renderedRadio;

	private boolean renderedCheck;

	private RespostaAberta aberta;

	private boolean respostasObjetivas;

	private boolean respostasSubjetivas;

	private boolean fim;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		try {

			FacesContext fc = FacesContext.getCurrentInstance();

			String participanteHash = fc.getExternalContext().getRequestParameterMap().get("participantes");

			participantes = avaliacaoService.retornarParticipantesPorID(Long.parseLong(participanteHash));

			int idQuestaoRespondida = participantes.getContadorID();

			Long idQuestionario = participantes.getAvaliacaoParticipantes().getQuestionario().getId();

			questaoERespostas = avaliacaoService.getQuestaoERespostas(Long.valueOf(idQuestaoRespondida) + 1,
					idQuestionario);
			questao = (Questao) questaoERespostas.get(0);
			respostas = (List<Resposta>) questaoERespostas.get(1);

			respostasObjetivas = true;
			respostasSubjetivas = false;

			renderedCheck = false;
			renderedRadio = false;

			if (questao.getCheckin() == 1) {
				renderedCheck = true;
			} else {
				renderedRadio = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public String registraRespostas() throws SQLException, ClassNotFoundException {

		if (questao.getCheckin() == 1) {
			
			for (int i = 0; i <= idRespostasSelected.length;) {
				
				if(i == 0){
				
					avaliacaoService.registraContador(participantes.getId());
				}
				break;
			}

			for (String id : idRespostasSelected) {
				
				
				int idResposta = Integer.valueOf(id);
			
				avaliacaoService.registraResposta(participantes.getId(), idResposta);
				
			}

		} else if (questao.getSubjetivas() == 1) {

			avaliacaoService.registraAberta(participantes.getId(), idRespostaSelect, aberta.getRespostas());

		} else {

			Long idPart = participantes.getId();

			participantes = participanteService.retornarParticipantesPorID(idPart);

			avaliacaoService.registraResposta(participantes.getId(), idRespostaSelect);
			avaliacaoService.registraContador(idPart);

		}

		participantes.setContadorID(participantes.getContadorID() + 1);
		int idQuestaoRespondida = participantes.getContadorID();

		Long idQuestionario = participantes.getAvaliacaoParticipantes().getQuestionario().getId();

		questaoERespostas = avaliacaoService.getQuestaoERespostas(Long.valueOf(idQuestaoRespondida) + 1,
				idQuestionario);
		questao = (Questao) questaoERespostas.get(0);
		respostas = (List<Resposta>) questaoERespostas.get(1);

		renderedCheck = false;
		renderedRadio = false;
		

		if (questao.getCheckin() == 1) {
			renderedCheck = true;
		} else if(questao.getNumeroQuestao_id() == 0){
			respostasObjetivas = false;
	        fim = true;
		}else{
			renderedRadio = true;
		}

		switch (questao.getSubjetivas()) {
	
		case 1:

			respostasObjetivas = false;
			respostasSubjetivas = true;

			aberta = new RespostaAberta();

			break;

		}
		
		
		
		return null;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public List getQuestaoERespostas() {
		return questaoERespostas;
	}

	@SuppressWarnings("rawtypes")
	public void setQuestaoERespostas(List questaoERespostas) {
		this.questaoERespostas = questaoERespostas;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public int getIdRespostaSelect() {
		return idRespostaSelect;
	}

	public void setIdRespostaSelect(int idRespostaSelect) {
		this.idRespostaSelect = idRespostaSelect;
	}

	public Resposta getRespostaSelected() {
		return respostaSelected;
	}

	public void setRespostaSelected(Resposta respostaSelected) {
		this.respostaSelected = respostaSelected;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public String[] getIdRespostasSelected() {
		return idRespostasSelected;
	}

	public void setIdRespostasSelected(String[] idRespostasSelected) {
		this.idRespostasSelected = idRespostasSelected;
	}

	public Participantes getParticipantes() {
		return participantes;
	}

	public void setParticipantes(Participantes participantes) {
		this.participantes = participantes;
	}

	public boolean isFim() {
		return fim;
	}

	public void setFim(boolean fim) {
		this.fim = fim;
	}

	public String getTituloGrupo() {
		return tituloGrupo;
	}

	public void setTituloGrupo(String tituloGrupo) {
		this.tituloGrupo = tituloGrupo;
	}

	public String[] getRespostasSelected() {
		return respostasSelected;
	}

	public void setRespostasSelected(String[] respostasSelected) {
		this.respostasSelected = respostasSelected;
	}

	public String getRespostaRespostaSelect() {
		return respostaRespostaSelect;
	}

	public void setRespostaRespostaSelect(String respostaRespostaSelect) {
		this.respostaRespostaSelect = respostaRespostaSelect;
	}

	public boolean isRenderedRadio() {
		return renderedRadio;
	}

	public void setRenderedRadio(boolean renderedRadio) {
		this.renderedRadio = renderedRadio;
	}

	public boolean isRenderedCheck() {
		return renderedCheck;
	}

	public void setRenderedCheck(boolean renderedCheck) {
		this.renderedCheck = renderedCheck;
	}

	public RespostaAberta getAberta() {
		return aberta;
	}

	public void setAberta(RespostaAberta aberta) {
		this.aberta = aberta;
	}

	public boolean isRespostasObjetivas() {
		return respostasObjetivas;
	}

	public void setRespostasObjetivas(boolean respostasObjetivas) {
		this.respostasObjetivas = respostasObjetivas;
	}

	public boolean isRespostasSubjetivas() {
		return respostasSubjetivas;
	}

	public void setRespostasSubjetivas(boolean respostasSubjetivas) {
		this.respostasSubjetivas = respostasSubjetivas;
	}

}
