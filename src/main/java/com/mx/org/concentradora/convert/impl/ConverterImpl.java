package com.mx.org.concentradora.convert.impl;


import org.springframework.stereotype.Component;

import com.mx.org.concentradora.convert.Converter;
import com.mx.org.concentradora.entity.Request;
import com.mx.org.concentradora.model.RequestModel;

@Component("converter")
public class ConverterImpl implements Converter {

	@Override
	public Request modelToEntity(RequestModel model) {
		Request entity = new Request(
				model.getTclave(), model.getCaja(), model.getIdTurno(), model.getIdTicket(), model.getiClave(), 
				model.getPclave(), model.getIcb(), model.getFolio(), model.getFecha(), model.getEstatus(), 
				model.getAtmFecha(), model.getUserId(), model.getMonto(), model.getReferencia());
		return entity;
	}

	@Override
	public RequestModel EntityToModel(Request entity) {
		RequestModel model = new RequestModel(
				entity.getTclave(), entity.getCaja(), entity.getIdturno(), entity.getIdticket(), entity.getIclave(), 
				entity.getPclave(), entity.getIcb(), entity.getFolio(), entity.getFecha(), entity.getEstatus(), 
				entity.getAtmFecha(), entity.getUserId(), entity.getMonto(), entity.getReferencia());
				return model;
	}
}
