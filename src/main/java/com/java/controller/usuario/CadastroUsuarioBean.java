package com.java.controller.usuario;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.java.modelo.Empresa;
import com.java.modelo.Usuario;
import com.java.modeloEspecializado.AlteraSenhaUsuario;
import com.java.service.EmpresaService;
import com.java.service.UsuarioService;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private EmpresaService empresaService;

	private Usuario usuario;

	private AlteraSenhaUsuario alteraSenhaUsuario;

	private Map<String, Empresa> listarEmpresas = new HashMap<String, Empresa>();

	@PostConstruct
	public void init() throws IOException, SQLException {
		this.limpar();
		preencheListasSelect();
	}

	public void salvar() {
		try {
			this.usuarioService.salvar(usuario);
			FacesUtil.addSuccessMessage("Usuário salvo com sucesso!");
			this.limpar();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	

	private void preencheListasSelect() throws SQLException {

		listarEmpresas = new HashMap<String, Empresa>();
		for (Empresa emp : empresaService.listarTodos()) {
			listarEmpresas.put(emp.getNome(), emp);
		}

	}

	public void alterarSenhaUsuario() {

		try {

			this.alteraSenhaUsuario = new AlteraSenhaUsuario();

			this.alteraSenhaUsuario.setId(this.usuario.getId());
			this.alteraSenhaUsuario.setLogin(this.usuario.getLogin());
			this.alteraSenhaUsuario.setNovaSenha(this.usuario.getSenha());

			this.usuarioService.alterarSenhaUsuario(this.alteraSenhaUsuario);
			FacesUtil.addSuccessMessage("Senha alterada com sucesso!");
			this.limpar();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public void limpar() {
		this.usuario = new Usuario();
		this.alteraSenhaUsuario = new AlteraSenhaUsuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AlteraSenhaUsuario getAlteraSenhaUsuario() {
		return alteraSenhaUsuario;
	}

	public void setAlteraSenhaUsuario(AlteraSenhaUsuario alteraSenhaUsuario) {
		this.alteraSenhaUsuario = alteraSenhaUsuario;
	}

	public Map<String, Empresa> getListarEmpresas() {
		return listarEmpresas;
	}

	public void setListarEmpresas(Map<String, Empresa> listarEmpresas) {
		this.listarEmpresas = listarEmpresas;
	}

}
