package com.mx.org.concentradora.service.impl;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.org.concentradora.client.GeneradorPeticionesMock;
import com.mx.org.concentradora.client.MockSolicitudSaldoClient;
import com.mx.org.concentradora.model.TransaccionIn;
import com.mx.org.concentradora.service.TaeService;
import com.mx.org.concentradora.util.TransaccionUtil;

@Service("taeService")
public class TaeServiceImpl implements TaeService {

	@Autowired
	GeneradorPeticionesMock mock;

	@Autowired
	MockSolicitudSaldoClient saldo;

	public String[] enviaSolicitud(TransaccionIn transaccion) throws UnknownHostException {
		String accion = "01";

		if (transaccion.getProducto() != null && !transaccion.getProducto().isEmpty()) {
			accion = "21";
		}

		String respuetaSocket = saldo.sendMessage(mock.generarPeticionSolicitudSaldo(transaccion.getTclave(),
				transaccion.getCaja(), transaccion.getReferencia(),
				TransaccionUtil.transformarMontoCadena(transaccion.getMonto()), accion, transaccion.getProducto()));
		String[] respuestaProcesada = TransaccionUtil.procesarRespuestaSocket(respuetaSocket);
		return respuestaProcesada;
	}

	@Override
	public String[] enviaEcho() throws UnknownHostException {
		String respuetaSocket = saldo.sendMessage(mock.generarPeticionEcho());
		String[] respuestaProcesada = TransaccionUtil.procesarRespuestaSocket(respuetaSocket);
		return respuestaProcesada;
	}
}
