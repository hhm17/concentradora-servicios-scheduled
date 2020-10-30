package com.mx.org.concentradora.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("generadorPeticionesMock")
public class GeneradorPeticionesMock {

	private static int contador = 0;
	private static Date fechaActual = new Date();

	public String generarPeticionEcho() {
		contador++;
		StringBuilder peticion = new StringBuilder();
		peticion.append("STX");
		peticion.append("98");
		/** hhm: se debera poner codigo de 3B proporcionado por proveedor **/
		peticion.append("3B");
		peticion.append(rellenarCeros(6, String.valueOf(contador)));
		peticion.append(generarCadenaFecha());
		peticion.append(generarCadenaHora());
		peticion.append("ETX");
		return peticion.toString();
	}

	public String generarPeticionSolicitudSaldo(String tclave, String caja, String celular, String monto, String accion,
			String producto) {
		StringBuilder peticion = new StringBuilder();
		/*********************** parte fija **********************************/
		peticion.append("STX");
		peticion.append(accion);

		/** hhm: ID transaccion canal de venta **/
		peticion.append("3B");
		peticion.append(rellenarCeros(6, String.valueOf(contador)));

		/** hhm: fecha y hora **/
		peticion.append(generarCadenaFecha());
		peticion.append(generarCadenaHora());
		/***********************************************************************/

		/************************** parte variable *****************************/
		/** hhm: puede ir en 0 cadena comercial **/
		peticion.append("3B");
		peticion.append(rellenarCeros(8, String.valueOf(contador)));

		/** hhm: tclave **/
		peticion.append(rellenarCeros(5, tclave));

		/** hhm: caja **/
		peticion.append(rellenarCeros(10, caja));

		/** hhm: fecha y hora **/
		peticion.append(generarCadenaFecha());
		peticion.append(generarCadenaHora());

		/** hhm: folio **/
		peticion.append(rellenarCeros(10, String.valueOf(contador)));

		/** hhm: telefono **/
		peticion.append(rellenarCeros(10, celular));

		/** hhm: monto ultimos dos decimales **/
		peticion.append(rellenarCeros(10, monto));

		/** hhm: producto solo para paquete de datos **/
		if (producto != null && !producto.isEmpty()) {
			peticion.append(rellenarCeros(10, producto));
		}

		peticion.append("ETX");
		/***********************************************************************/
		return peticion.toString();
	}

	public static String rellenarCeros(int ceros, String numeroCadena) {
		String cadenaCeros = "";

		int numeroCeros = ceros - numeroCadena.length();
		for (int i = 0; i < numeroCeros; i++) {
			cadenaCeros += "0";
		}
		cadenaCeros += numeroCadena;
		return cadenaCeros;
	}

	private static String generarCadenaFecha() {
		String cadenaFecha = "";
		cadenaFecha = new SimpleDateFormat("ddMMyyyy").format(fechaActual);
		return cadenaFecha;
	}

	private static String generarCadenaHora() {
		String cadenaHora = "";
		cadenaHora = new SimpleDateFormat("HHmmss").format(fechaActual);
		return cadenaHora;
	}
}
