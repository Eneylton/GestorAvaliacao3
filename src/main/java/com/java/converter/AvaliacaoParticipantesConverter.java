package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.AvaliacaoParticipantesDAO;
import com.java.modelo.AvaliacaoParticipantes;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=AvaliacaoParticipantes.class)
public class AvaliacaoParticipantesConverter implements Converter {

	private AvaliacaoParticipantesDAO avaliacaoParticipantesDAO;
	
	public AvaliacaoParticipantesConverter() {
		this.avaliacaoParticipantesDAO = CDIServiceLocator.getBean(AvaliacaoParticipantesDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		AvaliacaoParticipantes retorno = null;
		
		try {
		
			if (value != null) {
				retorno = this.avaliacaoParticipantesDAO.retornarAvaliacaoParticipantesPorID(new Long(value));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((AvaliacaoParticipantes) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		
		return "";
	}

}