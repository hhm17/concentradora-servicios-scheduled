package com.mx.org.concentradora.service;

import java.net.UnknownHostException;

import com.mx.org.concentradora.model.TransaccionOut;

public interface TaeService {

	public abstract String[] taeTelcel(TransaccionOut transaccion) throws UnknownHostException;

}
