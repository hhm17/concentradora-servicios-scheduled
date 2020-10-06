package com.mx.org.concentradora.convert;

import com.mx.org.concentradora.entity.Request;
import com.mx.org.concentradora.model.RequestModel;

public interface Converter {

	public abstract Request modelToEntity(RequestModel model);
	public abstract RequestModel EntityToModel(Request rq); 
	
	
}
