package com.mx.org.concentradora.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mx.org.concentradora.model.Bitacora;
import com.mx.org.concentradora.model.TransaccionIn;
import com.mx.org.concentradora.model.TransaccionOut;

public class TransaccionUtil {

	private static Date fechaActual = new Date();

	public static String rellenarCeros(int ceros, Long numero) {
		String cadenaCeros = "";
		String numeroCadena = String.valueOf(numero);

		int numeroCeros = ceros - numeroCadena.length();
		for (int i = 0; i < numeroCeros; i++) {
			cadenaCeros += "0";
		}
		cadenaCeros += numeroCadena;
		return cadenaCeros;
	}

	public static TransaccionOut convertirTransaccionOut(TransaccionIn transaccionEntrada) {
		TransaccionOut transaccionSalida = new TransaccionOut();
		transaccionSalida.setAtmFecha(transaccionEntrada.getAtmFecha());
		transaccionSalida.setCaja(transaccionEntrada.getCaja());
		transaccionSalida.setEstatus(transaccionEntrada.getEstatus());
		transaccionSalida.setFecha(transaccionEntrada.getFecha());
		transaccionSalida.setIcb(transaccionEntrada.getIcb());
		transaccionSalida.setIclave(transaccionEntrada.getIclave());
		transaccionSalida.setId(transaccionEntrada.getId());
		transaccionSalida.setIdTicket(transaccionEntrada.getIdTicket());
		transaccionSalida.setIdTurno(transaccionEntrada.getIdTurno());
		transaccionSalida.setMonto(transaccionEntrada.getMonto());
		transaccionSalida.setPclave(transaccionEntrada.getPclave());
		transaccionSalida.setReferencia(transaccionEntrada.getReferencia());
		transaccionSalida.setTclave(transaccionEntrada.getTclave());
		transaccionSalida.setUserId(transaccionEntrada.getUserId());
		transaccionSalida.setFolio(String.valueOf(transaccionEntrada.getId()));
		return transaccionSalida;
	}

	public static Bitacora crearBitacora(TransaccionIn transaccionEntrada) {
		Bitacora bitacora = new Bitacora();
		bitacora.setCaja(transaccionEntrada.getCaja());
		bitacora.setEstatus(transaccionEntrada.getEstatus());
		bitacora.setIcb(transaccionEntrada.getIcb());
		bitacora.setIclave(transaccionEntrada.getIclave());
		bitacora.setId(transaccionEntrada.getId());
		bitacora.setIdTicket(transaccionEntrada.getIdTicket());
		bitacora.setIdTurno(transaccionEntrada.getIdTurno());
		bitacora.setMonto(transaccionEntrada.getMonto());
		bitacora.setPclave(transaccionEntrada.getPclave());
		bitacora.setReferencia(transaccionEntrada.getReferencia());
		bitacora.setTclave(transaccionEntrada.getTclave());
		bitacora.setUserId(transaccionEntrada.getUserId());
		bitacora.setFolio(String.valueOf(transaccionEntrada.getId()));
		return bitacora;
	}

	public static String generarCadenaFecha() {
		String cadenaFecha = "";
		cadenaFecha = new SimpleDateFormat("ddMMyyyy").format(fechaActual);
		return cadenaFecha;
	}

	public static String generarCadenaHora() {
		String cadenaHora = "";
		cadenaHora = new SimpleDateFormat("HHmmss").format(fechaActual);
		return cadenaHora;
	}

	public static String[] procesarRespuestaSocket(String respuesta) {
		String[] arr = new String[3];
		arr[1] = respuesta.substring(respuesta.length() - 9, respuesta.length() - 3);
		arr[0] = respuesta.substring(respuesta.length() - 11, respuesta.length() - 9);
		arr[2] = respuesta.substring(5, 13);
		return arr;
	}

	public static String transformarMontoCadena(double monto) {
		String montoCadena = String.valueOf(monto).replace(".", "");
		return montoCadena;
	}
}
