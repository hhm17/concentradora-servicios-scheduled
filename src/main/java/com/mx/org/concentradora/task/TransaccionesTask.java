package com.mx.org.concentradora.task;

import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mx.org.concentradora.client.TransaccionInFeignClient;
import com.mx.org.concentradora.client.TransaccionOutFeignClient;
import com.mx.org.concentradora.model.TransaccionIn;
import com.mx.org.concentradora.model.TransaccionOut;
import com.mx.org.concentradora.service.TaeService;
import com.mx.org.concentradora.util.TransaccionUtil;

@Component
public class TransaccionesTask {

	private Integer TRANSACCION_NUEVA = 1;
	private Integer TRANSACCION_ENVIADA = 2;
	private Integer TRANSACCION_RESUELTA = 3;

	@Autowired
	private TransaccionInFeignClient transaccionInFeignClient;

	@Autowired
	private TransaccionOutFeignClient transaccionOutFeignClient;

	@Autowired
	@Qualifier("taeService")
	private TaeService taeService;

	@Scheduled(cron = "0 05 16 * * ?")
	public void procesarTransacciones() {
		try {
			enviaEcho();

			CollectionModel<TransaccionIn> transaccionesNuevas = transaccionInFeignClient.findByEstatus(TRANSACCION_NUEVA);
			if (transaccionesNuevas != null) {
				for (TransaccionIn transaccionIn : transaccionesNuevas.getContent()) {
					TransaccionOut transaccionOut = TransaccionUtil.convertirTransaccionOut(transaccionIn);
					transaccionOut.setEstatus(TRANSACCION_ENVIADA);
					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());
					
					String[] respuestaSocket = taeService.enviaSolicitud(transaccionOut);
					transaccionOut.setEstatus(TRANSACCION_RESUELTA);
					transaccionOut.setRespProv(respuestaSocket[0]);
					transaccionOut.setFolioProv(respuestaSocket[1]);
					transaccionOut.setCanalVenta(respuestaSocket[2]);
					transaccionOut.setFechaResp(new Date());

					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void enviaEcho() throws UnknownHostException {
		taeService.enviaEcho();
	}
}
