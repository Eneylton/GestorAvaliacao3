package com.java.controller.estatistica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.java.modelo.Estatistica;
import com.java.modelo.Questao;
import com.java.modelo.Questionario;
import com.java.service.EstatisticaService;
import com.java.service.QuestaoService;
import com.java.service.QuestionarioService;
import com.java.service.RelatorioService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstatisticaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PieChartModel pieModelTipo = new PieChartModel();

	@Inject
	private EstatisticaService estatisticaService;

	@Inject
	private RelatorioService relatorioService;

	@Inject
	private QuestaoService questaoService;

	@Inject
	private QuestionarioService questionarioService;

	private Questionario questionario = new Questionario();

	private Map<String, Questionario> listarQuestionarios = new HashMap<String, Questionario>();

	private Map<String, Questao> listarQuestoes = new HashMap<String, Questao>();

	private List<Estatistica> listarRespostas = new ArrayList<>();

	private List<Questao> listarQuestoesporQuestionario = new ArrayList<>();

	private Estatistica estatistica = new Estatistica();

	private Questao questao = new Questao();

	private Date dataInicio;
	private Date dataFim;

	@PostConstruct
	public void init() {

		try {

			limpar();

			preencheListasSelect();

			consultar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no Processamento - Erro: " + ex.getMessage());
		}

	}

	public void consultar() throws SQLException {

		Long idQuestao = questao.getId();
		Long idQuestionario = questionario.getId();

		this.dataInicio = estatistica.getDataInicio();
		this.dataFim = estatistica.getDataFim();

		if (idQuestao == null) {

			listarRespostas = estatisticaService.listarTodos();
			graficoTodos();

		} else {

			listarRespostas = estatisticaService.listarTodosPorQuestao(idQuestao,idQuestionario,this.dataInicio, this.dataFim);

			grafico();
		}

	}

	public void gerarGrafico() {

		try {
			Long idQuestao = questao.getId();
			Long idQuestionario = questionario.getId();

			this.dataInicio = estatistica.getDataInicio();
			this.dataFim = estatistica.getDataFim();

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("questao_id", idQuestao);
			parametros.put("idQuestionario", idQuestionario);
			parametros.put("dataInicio", estatistica.getDataInicio());
			parametros.put("dataFim", estatistica.getDataFim());
			listarRespostas = estatisticaService.listarTodosPorQuestao(idQuestao,idQuestionario, this.dataInicio, this.dataFim);
			relatorioService.gerarGaficoPDF(listarRespostas, (HashMap<String, Object>) parametros, "GraficoEstatistico");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void graficoTodos() throws SQLException {

		pieModelTipo = new PieChartModel();

		listarRespostas = estatisticaService.listarTodos();

		for (Estatistica estatistica : listarRespostas) {
			
			int porcento = estatistica.getContador() * 2 / 100 ;

			pieModelTipo.set(estatistica.getResposta() + " -- (" + porcento + " % )",
					estatistica.getContador());

		}

		pieModelTipo.setLegendPosition("w");
		/* pieModelTipo.setLegendPlacement(LegendPlacement.OUTSIDEGRID); */
		pieModelTipo.setFill(true);

		pieModelTipo.setShowDataLabels(true);

		pieModelTipo.setDiameter(250);
		pieModelTipo.setMouseoverHighlight(true);
		pieModelTipo.setShadow(true);

		pieModelTipo.setSeriesColors("5A1846,910D3F,C70039,FF5733,FFC301," + "FF0000,FFEB3B,3F51B5,8EFF0B,AB3AB7,"
				+ "37A8A5,77CDBE,C73326,5E412E,52828C," + "003051,00A5B7,CADF00,DB6A5F,F0AE00");

	}

	public void grafico() throws SQLException {

		pieModelTipo = new PieChartModel();
		
		Long idQuestao = questao.getId();
		Long idQuestionario = questionario.getId();
		
		
		this.dataInicio = estatistica.getDataInicio();
		this.dataFim = estatistica.getDataFim();
		listarRespostas = estatisticaService.listarTodosPorQuestao(idQuestao,idQuestionario,this.dataInicio, this.dataFim);

		for (Estatistica estatistica : listarRespostas) {
			
			int valor = estatistica.getContador() ;
			
			double porceto = (valor * 100);
			
			double valorFinal = (porceto) / 100;

			pieModelTipo.set(estatistica.getResposta() + " -- (" + valorFinal  + " %)",
					estatistica.getContador());

		}

		pieModelTipo.setLegendPosition("w");
		/* pieModelTipo.setLegendPlacement(LegendPlacement.OUTSIDEGRID); */
		pieModelTipo.setFill(true);

		pieModelTipo.setShowDataLabels(true);

		pieModelTipo.setDiameter(250);
		pieModelTipo.setMouseoverHighlight(true);
		pieModelTipo.setShadow(true);
		pieModelTipo.setSeriesColors("5A1846,910D3F,C70039,FF5733,FFC301," + "FF0000,FFEB3B,3F51B5,8EFF0B,AB3AB7,"
				+ "37A8A5,77CDBE,C73326,5E412E,52828C," + "003051,00A5B7,CADF00,DB6A5F,F0AE00");

	}

	private void preencheListasSelect() throws SQLException {

		listarQuestoes = new TreeMap<String, Questao>();
		for (Questao questao : questaoService.listarTodos()) {
			listarQuestoes.put(questao.getQuestao(), questao);
		}

		listarQuestionarios = new TreeMap<String, Questionario>();
		for (Questionario questionario : questionarioService.listarTodos()) {
			listarQuestionarios.put(questionario.getTitulo(), questionario);
		}

	}

	public void popular() throws SQLException {

		if (questionario.getId() != null) {

			Long idQuest = questionario.getId();

			listarQuestoesporQuestionario = estatisticaService.buscarQuestaoporQuestionario(idQuest);

		}

	}

	public void limpar() {
		this.estatistica = new Estatistica();
	}

	public Estatistica getEstatistica() {
		return estatistica;
	}

	public void setEstatistica(Estatistica estatistica) {
		this.estatistica = estatistica;
	}

	public List<Estatistica> getListarRespostas() {
		return listarRespostas;
	}

	public void setListarRespostas(List<Estatistica> listarRespostas) {
		this.listarRespostas = listarRespostas;
	}

	public Map<String, Questao> getListarQuestoes() {
		return listarQuestoes;
	}

	public void setListarQuestoes(Map<String, Questao> listarQuestoes) {
		this.listarQuestoes = listarQuestoes;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public PieChartModel getPieModelTipo() {
		return pieModelTipo;
	}

	public void setPieModelTipo(PieChartModel pieModelTipo) {
		this.pieModelTipo = pieModelTipo;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public Map<String, Questionario> getListarQuestionarios() {
		return listarQuestionarios;
	}

	public void setListarQuestionarios(Map<String, Questionario> listarQuestionarios) {
		this.listarQuestionarios = listarQuestionarios;
	}

	public List<Questao> getListarQuestoesporQuestionario() {
		return listarQuestoesporQuestionario;
	}

	public void setListarQuestoesporQuestionario(List<Questao> listarQuestoesporQuestionario) {
		this.listarQuestoesporQuestionario = listarQuestoesporQuestionario;
	}

}
