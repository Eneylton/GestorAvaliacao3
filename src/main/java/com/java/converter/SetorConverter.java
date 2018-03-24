package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.SetorDAO;
import com.java.modelo.Setor;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Setor.class)
public class SetorConverter implements Converter {

	private SetorDAO setorDAO;

	public SetorConverter() {
		this.setorDAO = CDIServiceLocator.getBean(SetorDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Setor retorno = null;

		try {
			if (value != null) {
				retorno = this.setorDAO.retornarSetorPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Setor) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}