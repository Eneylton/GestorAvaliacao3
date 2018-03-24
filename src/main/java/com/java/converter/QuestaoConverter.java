package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.QuestaoDAO;
import com.java.modelo.Questao;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Questao.class)
public class QuestaoConverter implements Converter {

	private QuestaoDAO questaoDAO;
	
	public QuestaoConverter() {
		this.questaoDAO = CDIServiceLocator.getBean(QuestaoDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Questao retorno = null;
		
		try {
			if (value != null) {
			retorno = this.questaoDAO.retornarQuestaoPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Questao) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}