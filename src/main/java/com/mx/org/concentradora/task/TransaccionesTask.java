package com.mx.org.concentradora.task;

import java.net.UnknownHostException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.CollectionModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mx.org.concentradora.client.BitacoraFeignClient;
import com.mx.org.concentradora.client.MockSolicitudSaldoTaeClient;
import com.mx.org.concentradora.client.MockSolicitudSaldoVsClient;
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
	private Integer TRANSACCION_ERRONEA = 0;
	private static int intentosEcho = 0;
	private boolean inicio = true;

	private Integer BITACORA_TRANSACCION_ENVIADA = 2;
	private Integer BITACORA_TRANSACCION_PROCESADA = 3;
	private Integer BITACORA_TRANSACCION_ERRONEA = 0;

	@Autowired
	private TransaccionInFeignClient transaccionInFeignClient;

	@Autowired
	private TransaccionOutFeignClient transaccionOutFeignClient;

	@Autowired
	private BitacoraFeignClient bitacoraFeignClient;

	@Autowired
	@Qualifier("taeService")
	private TaeService taeService;

	@Autowired
	private MockSolicitudSaldoTaeClient saldoTae;

	@Autowired
	private MockSolicitudSaldoVsClient saldoVs;

	@Autowired
	private Environment env;

	@PostConstruct
	public void iniciarTask() {
		abrirConexionTae();
		abrirConexionVs();
	}

	public void abrirConexionTae() {
		String ip = env.getProperty("ip.socket.server.tae");
		String puerto = env.getProperty("puerto.socket.server.tae");
		try {
			int nPuerto = Integer.parseInt(puerto);
			saldoTae.startConnection(ip, nPuerto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abrirConexionVs() {
		String ip = env.getProperty("ip.socket.server.vs");
		String puerto = env.getProperty("puerto.socket.server.vs");
		try {
			int nPuerto = Integer.parseInt(puerto);
			saldoVs.startConnection(ip, nPuerto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "*/10 * * ? * *")
	public void procesarTransacciones() {
		if (intentosEcho == 0 && inicio) {
			try {
				System.out.println("********** Enviando echo por inicio **************");
				enviaEchoTae();
				enviaEchoVs();
			} catch (UnknownHostException e) {
				System.out.println("********** Error en echo por inicio **************");
				e.printStackTrace();
			}
			inicio = false;
		}
		CollectionModel<TransaccionIn> transaccionesNuevas = transaccionInFeignClient.findByEstatus(TRANSACCION_NUEVA);
		System.out.println("********** Leyo transacciones pendientes **************");
		if (transaccionesNuevas != null && !transaccionesNuevas.getContent().isEmpty()) {
			intentosEcho = 0;
			System.out.println("********** Existen transacciones pendientes **************");
			for (TransaccionIn transaccionIn : transaccionesNuevas.getContent()) {
				TransaccionOut transaccionOut = TransaccionUtil.convertirTransaccionOut(transaccionIn);
				try {
					System.out.println("********** Actualizando transaccion de salida **************");
					transaccionOut.setEstatus(TRANSACCION_ENVIADA);
					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());

					System.out.println("********** Actualizando transaccion de entrada **************");
					transaccionIn.setEstatus(TRANSACCION_ENVIADA);
					transaccionInFeignClient.update(transaccionIn, transaccionIn.getId());

					System.out.println("********** Buscando bitacora **************");
					/** hhm: se guarda bitacora con transaccion de entrada enviada **/
					Bitacora bitacora = bitacoraFeignClient.findByFolio(transaccionOut.getFolio());
					bitacora.setEstatus(BITACORA_TRANSACCION_ENVIADA);
					bitacora.setFechaActualizacion(new Date());
					System.out.println("********** Actualizando bitacora **************");
					bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());

					String[] respuestaSocket;

					System.out.println("********** Enviado a socket **************");
					respuestaSocket = taeService.enviaSolicitud(transaccionIn);
					System.out.println("********** Se recibio respuesta de socket **************");
					transaccionOut.setEstatus(TRANSACCION_RESUELTA);
					transaccionOut.setRespProv(respuestaSocket[0]);
					transaccionOut.setFolioProv(respuestaSocket[1]);
					transaccionOut.setCanalVenta(respuestaSocket[2]);
					transaccionOut.setLeyendaTck(procesarRespuestaSocket(respuestaSocket[0]));
					transaccionOut.setFechaResp(new Date());

					System.out.println("********** Actualizando transaccion con respuesta de socket **************");
					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());

					/** hhm: se guarda bitacora con transaccion de salida resuelta **/
					bitacora.setEstatus(BITACORA_TRANSACCION_PROCESADA);
					bitacora.setFechaActualizacion(new Date());
					bitacora.setFechaFin(new Date());
					bitacora.setLeyendaTck(procesarRespuestaSocket(respuestaSocket[0]));
					bitacora.setRespProv(respuestaSocket[0]);
					bitacora.setFolioProv(respuestaSocket[1]);
					System.out.println("********** Actualizando bitacora con respuesta de socket **************");
					bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());

					System.out.println("********** Actualizando transaccion de entrada **************");
					transaccionIn.setEstatus(TRANSACCION_RESUELTA);
					transaccionInFeignClient.update(transaccionIn, transaccionIn.getId());
				} catch (Exception e) {
					transaccionIn.setEstatus(TRANSACCION_ERRONEA);
					System.out.println("********** Actualizando transaccion entrada erronea **************");
					transaccionInFeignClient.update(transaccionIn, transaccionIn.getId());

					System.out.println("********** Fallo la actualizacion o envio a socket **************");
					transaccionOut.setEstatus(TRANSACCION_ERRONEA);
					transaccionOut.setRespProv("E01");
					transaccionOut.setLeyendaTck("Ocurrio un error: " + e.getMessage());
					transaccionOut.setFechaResp(new Date());
					System.out.println("********** Actualizando transaccion erronea **************");
					transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());

					/** hhm: se guarda bitacora con transaccion de entrada erronea **/
					System.out.println("********** Buscando bitacora erronea **************");
					Bitacora bitacora = bitacoraFeignClient.findByFolio(transaccionOut.getFolio());
					bitacora.setEstatus(BITACORA_TRANSACCION_ERRONEA);
					bitacora.setFechaActualizacion(new Date());
					bitacora.setFechaFin(new Date());
					bitacora.setRespProv("E01");
					bitacora.setLeyendaTck("Ocurrio un error: " + e.getMessage());
					System.out.println("********** Actualizando bitacora erronea **************");
					bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());
				}
			}
		} else {
			intentosEcho++;
		}

		System.out.println("Ejecuciones sin transacciones por procesar: " + intentosEcho);
		String contadorEchos = env.getProperty("numero.contador.echos");
		int nContadorEchos = Integer.parseInt(contadorEchos);
		if (intentosEcho == nContadorEchos) {
			intentosEcho = 0;
			try {
				System.out.println("********** Enviando echo por intentos **************");
				enviaEchoTae();
				enviaEchoVs();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

	private String procesarRespuestaSocket(String codigoRespuesta) {
		String codigos = env.getProperty("codigo.respuesta.telcel");
		String respuestaTexto = "";
		String[] codigosSeparados = codigos.split("@");

		for (String codigo : codigosSeparados) {
			if (codigo.contains(codigoRespuesta)) {
				String[] respuesta = codigo.split("\\|");
				respuestaTexto = respuesta[1];
			}
		}

		if (respuestaTexto.isEmpty()) {
			respuestaTexto = "Codigo de respuesta incorrecto.";
		}

		return respuestaTexto;
	}

	// @Scheduled(cron = "*/10 * * ? * *")
	// public void procesarTransacciones() {
	// try {
	// if (intentosEcho == 0 && inicio) {
	// enviaEcho();
	// inicio = false;
	// }
	// CollectionModel<TransaccionIn> transaccionesNuevas = transaccionInFeignClient
	// .findByEstatus(TRANSACCION_NUEVA);
	// if (transaccionesNuevas != null &&
	// !transaccionesNuevas.getContent().isEmpty()) {
	// intentosEcho = 0;
	// for (TransaccionIn transaccionIn : transaccionesNuevas.getContent()) {
	// TransaccionOut transaccionOut =
	// TransaccionUtil.convertirTransaccionOut(transaccionIn);
	// transaccionOut.setEstatus(TRANSACCION_ENVIADA);
	// transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());
	//
	// transaccionIn.setEstatus(TRANSACCION_ENVIADA);
	// transaccionInFeignClient.update(transaccionIn, transaccionIn.getId());
	//
	// /** hhm: se guarda bitacora con transaccion de entrada enviada **/
	// Bitacora bitacora =
	// bitacoraFeignClient.findByFolio(transaccionOut.getFolio());
	// bitacora.setEstatus(BITACORA_TRANSACCION_ENVIADA);
	// bitacora.setFechaActualizacion(new Date());
	// bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());
	//
	// String[] respuestaSocket = taeService.enviaSolicitud(transaccionIn);
	// transaccionOut.setEstatus(TRANSACCION_RESUELTA);
	// transaccionOut.setRespProv(respuestaSocket[0]);
	// transaccionOut.setFolioProv(respuestaSocket[1]);
	// transaccionOut.setCanalVenta(env.getProperty("codigo.3b.telcel"));
	// transaccionOut.setLeyendaTck(respuestaSocket[2]);
	// transaccionOut.setFechaResp(new Date());
	// transaccionOutFeignClient.update(transaccionOut, transaccionIn.getId());
	//
	// /** hhm: se guarda bitacora con transaccion de salida resuelta **/
	// bitacora.setEstatus(BITACORA_TRANSACCION_PROCESADA);
	// bitacora.setFechaActualizacion(new Date());
	// bitacora.setFechaFin(new Date());
	// bitacora.setRespProv(respuestaSocket[0]);
	// bitacora.setFolioProv(respuestaSocket[1]);
	// bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());
	//
	// transaccionIn.setEstatus(TRANSACCION_RESUELTA);
	// transaccionInFeignClient.update(transaccionIn, transaccionIn.getId());
	// }
	// } else {
	// intentosEcho++;
	// }
	//
	// System.out.println("Ejecuciones sin transacciones por procesar: " +
	// intentosEcho);
	// String contadorEchos = env.getProperty("numero.contador.echos");
	// int nContadorEchos = Integer.parseInt(contadorEchos);
	// if (intentosEcho == nContadorEchos) {
	// intentosEcho = 0;
	// enviaEcho();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private void enviaEchoTae() throws UnknownHostException {
		String[] echo = taeService.enviaEchoTae();
		System.out.println("enviando enviaEchoTae: " + echo);
	}

	private void enviaEchoVs() throws UnknownHostException {
		String[] echo = taeService.enviaEchoVs();
		System.out.println("enviando enviaEchoVs: " + echo);
	}

	public void cerrarConexionTae() throws UnknownHostException {
		saldoTae.stopConnection();
	}

	public void cerrarConexionVs() throws UnknownHostException {
		saldoVs.stopConnection();
	}
}
