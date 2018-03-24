package com.java.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.java.dao.ParticipantesDAO;
import com.java.modelo.Participantes;
import com.java.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Participantes.class)
public class ParticipantesConverter implements Converter {

	private ParticipantesDAO participantesDAO;

	public ParticipantesConverter() {
		this.participantesDAO = CDIServiceLocator.getBean(ParticipantesDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Participantes retorno = null;

		try {
			if (value != null) {
				retorno = this.participantesDAO.retornarParticipantesPorID(new Long(value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Participantes) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			return retorno;
		}
		return "";
	}

}