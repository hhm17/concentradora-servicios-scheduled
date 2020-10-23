package com.mx.org.concentradora.task;

import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mx.org.concentradora.client.BitacoraFeignClient;
import com.mx.org.concentradora.client.TransaccionInFeignClient;
import com.mx.org.concentradora.client.TransaccionOutFeignClient;
import com.mx.org.concentradora.model.Bitacora;
import com.mx.org.concentradora.model.TransaccionIn;
import com.mx.org.concentradora.model.TransaccionOut;
import com.mx.org.concentradora.service.TaeService;
import com.mx.org.concentradora.util.TransaccionUtil;

@Component
public class TransaccionesTask {

	private Integer TRANSACCION_NUEVA = 1;
	private Integer TRANSACCION_ENVIADA = 2;
	private Integer TRANSACCION_RESUELTA = 3;

	private Integer BITACORA_TRANSACCION_ENVIADA = 2;
	private Integer BITACORA_TRANSACCION_PROCESADA = 3;

	@Autowired
	private TransaccionInFeignClient transaccionInFeignClient;

	@Autowired
	private TransaccionOutFeignClient transaccionOutFeignClient;

	@Autowired
	private BitacoraFeignClient bitacoraFeignClient;

	@Autowired
	@Qualifier("taeService")
	private TaeService taeService;

	@Scheduled(cron = "0 */1 * ? * *")
	public void procesarTransacciones() {
		try {
//			enviaEcho();

			CollectionModel<TransaccionIn> transaccionesNuevas = transaccionInFeignClient
					.findByEstatus(TRANSACCION_NUEVA);
			if (transaccionesNuevas != null) {
				for (TransaccionIn transaccionIn : transaccionesNuevas.getContent()) {
					TransaccionOut transaccionOut = TransaccionUtil.convertirTransaccionOut(transaccionIn);
					transaccionOut.setEstatus(TRANSACCION_ENVIADA);
					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());

					transaccionIn.setEstatus(TRANSACCION_ENVIADA);
					transaccionInFeignClient.update(transaccionIn, transaccionIn.getId());

					/** hhm: se guarda bitacora con transaccion de entrada enviada **/
					Bitacora bitacora = bitacoraFeignClient.findByFolio(transaccionOut.getFolio());
					bitacora.setEstatus(BITACORA_TRANSACCION_ENVIADA);
					bitacora.setFechaActualizacion(new Date());
					bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());

//					String[] respuestaSocket = taeService.enviaSolicitud(transaccionOut);
					transaccionOut.setEstatus(TRANSACCION_RESUELTA);
					transaccionOut.setRespProv("XXXXXX");
					transaccionOut.setFolioProv("XXXXXX");
					transaccionOut.setCanalVenta("XXXXXX");
					transaccionOut.setFechaResp(new Date());

					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());

					/** hhm: se guarda bitacora con transaccion de salida resuelta **/
					bitacora.setEstatus(BITACORA_TRANSACCION_PROCESADA);
					bitacora.setFechaActualizacion(new Date());
					bitacora.setFechaFin(new Date());
					bitacora.setRespProv("XXXXXX");
					bitacora.setFolioProv("XXXXXX");
					bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enviaEcho() throws UnknownHostException {
		String[] echo = taeService.enviaEcho();
		System.out.println("enviando echo: " + echo);
	}
}
