package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.QuestionarioDAO;
import com.java.modelo.Questionario;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Questionario.class)
public class QuestionarioConverter implements Converter {

	private QuestionarioDAO questionarioDAO;
	
	public QuestionarioConverter() {
		this.questionarioDAO = CDIServiceLocator.getBean(QuestionarioDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Questionario retorno = null;
		
		try {
			if (value != null) {
			retorno = this.questionarioDAO.retornarQuestionarioPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Questionario) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}