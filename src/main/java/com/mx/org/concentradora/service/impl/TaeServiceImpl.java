package com.mx.org.concentradora.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.org.concentradora.client.GeneradorPeticionesMock;
import com.mx.org.concentradora.client.MockSolicitudSaldoClient;
import com.mx.org.concentradora.model.TransaccionOut;
import com.mx.org.concentradora.service.TaeService;
import com.mx.org.concentradora.util.TransaccionUtil;

@Service("taeService")
public class TaeServiceImpl implements TaeService {

	@Autowired
	GeneradorPeticionesMock mock;

	@Autowired
	MockSolicitudSaldoClient saldo;

	public String[] taeTelcel(TransaccionOut transaccion) throws UnknownHostException {
		String ip = null;
		ip = InetAddress.getLocalHost().getHostAddress();
//		 ip = "23.99.193.253";
		saldo.startConnection(ip, 9898);
		String respuetaSocket = saldo
				.sendMessage(mock.generarPeticionSolicitudSaldo(transaccion.getTclave(), transaccion.getCaja(),
						transaccion.getReferencia(), TransaccionUtil.transformarMontoCadena(transaccion.getMonto())));
		saldo.stopConnection();
		String[] respuestaProcesada = TransaccionUtil.procesarRespuestaSocket(respuetaSocket);
		return respuestaProcesada;
	}
}
