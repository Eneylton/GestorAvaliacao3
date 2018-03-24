package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.RespostaDAO;
import com.java.modelo.Resposta;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Resposta.class)
public class RespostaConverter implements Converter {

	private RespostaDAO respostaDAO;
	
	public RespostaConverter() {
		this.respostaDAO = CDIServiceLocator.getBean(RespostaDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Resposta retorno = null;
		
		try {
			if (value != null) {
			retorno = this.respostaDAO.retornarRespostaPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Resposta) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}