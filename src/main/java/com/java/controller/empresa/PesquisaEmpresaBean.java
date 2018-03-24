package com.java.controller.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Empresa;
import com.java.service.EmpresaService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EmpresaService empresaService;
	
	

	private List<Empresa> listaEmpresas = new ArrayList<>();
	private List<Empresa> pesquisarEmpresas = new ArrayList<>();

	private Empresa empresa;

	private Empresa empresaSelecionado;

	@PostConstruct
	public void inicializar() {

		try {
			listaEmpresas = empresaService.listarTodos();
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

	}

	public void excluir() {

		try {
			empresaService.excluir(empresaSelecionado);
			this.listaEmpresas.remove(empresaSelecionado);
			FacesUtil.addSuccessMessage("Auto escola " + empresaSelecionado.getNome() + " excluída com sucesso.");
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

	}
	
	

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa Empresa) {
		this.empresa = Empresa;
	}

	public Empresa getEmpresaSelecionado() {
		return empresaSelecionado;
	}

	public void setEmpresaSelecionado(Empresa EmpresaSelecionado) {
		this.empresaSelecionado = EmpresaSelecionado;
	}

	public List<Empresa> getPesquisarEmpresas() {
		return pesquisarEmpresas;
	}

	public void setPesquisarEmpresas(List<Empresa> pesquisarEmpresas) {
		this.pesquisarEmpresas = pesquisarEmpresas;
	}

	

}