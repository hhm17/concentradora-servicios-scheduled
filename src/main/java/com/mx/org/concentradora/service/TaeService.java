package com.mx.org.concentradora.service;

import java.net.UnknownHostException;

import com.mx.org.concentradora.model.TransaccionOut;

public interface TaeService {

	public abstract String[] enviaSolicitud(TransaccionOut transaccion) throws UnknownHostException;
	
	public abstract String[] enviaEcho() throws UnknownHostException;

}
