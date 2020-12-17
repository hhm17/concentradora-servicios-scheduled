package com.mx.org.concentradora.service.impl;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.org.concentradora.client.GeneradorPeticionesMock;
import com.mx.org.concentradora.client.MockSolicitudSaldoTaeClient;
import com.mx.org.concentradora.client.MockSolicitudSaldoVsClient;
import com.mx.org.concentradora.model.TransaccionIn;
import com.mx.org.concentradora.service.TaeService;
import com.mx.org.concentradora.util.TransaccionUtil;

@Service("taeService")
public class TaeServiceImpl implements TaeService {

	@Autowired
	private GeneradorPeticionesMock mock;

	@Autowired
	private MockSolicitudSaldoTaeClient saldoTae;

	@Autowired
	private MockSolicitudSaldoVsClient saldoVs;

	public String[] enviaSolicitud(TransaccionIn transaccion) throws UnknownHostException {
		String accion = "01";
		String respuetaSocket = "";

		if (transaccion.getProducto() != null && !transaccion.getProducto().isEmpty()) {
			accion = "21";
			respuetaSocket = saldoVs
					.sendMessage(mock.generarPeticionSolicitudSaldo(transaccion.getTclave(), transaccion.getCaja(),
							transaccion.getReferencia(), TransaccionUtil.transformarMontoCadena(transaccion.getMonto()),
							accion, transaccion.getProducto(), String.valueOf(transaccion.getId())));
		} else {
			respuetaSocket = saldoTae
					.sendMessage(mock.generarPeticionSolicitudSaldo(transaccion.getTclave(), transaccion.getCaja(),
							transaccion.getReferencia(), TransaccionUtil.transformarMontoCadena(transaccion.getMonto()),
							accion, transaccion.getProducto(), String.valueOf(transaccion.getId())));
		}

		String[] respuestaProcesada = TransaccionUtil.procesarRespuestaSocket(respuetaSocket);
		return respuestaProcesada;
	}

	@Override
	public String[] enviaEchoTae() throws UnknownHostException {
		String respuetaSocket = saldoTae.sendMessage(mock.generarPeticionEcho(true));
		String[] respuestaProcesada = TransaccionUtil.procesarRespuestaSocket(respuetaSocket);
		return respuestaProcesada;
	}

	@Override
	public String[] enviaEchoVs() throws UnknownHostException {
		String respuetaSocket = saldoVs.sendMessage(mock.generarPeticionEcho(false));
		String[] respuestaProcesada = TransaccionUtil.procesarRespuestaSocket(respuetaSocket);
		return respuestaProcesada;
	}
}
