package com.mx.org.concentradora.service;

import java.net.UnknownHostException;

import com.mx.org.concentradora.model.TransaccionIn;

public interface TaeService {

	public abstract String[] enviaSolicitud(TransaccionIn transaccion) throws UnknownHostException;
	
	public abstract String[] enviaEcho() throws UnknownHostException;

}
