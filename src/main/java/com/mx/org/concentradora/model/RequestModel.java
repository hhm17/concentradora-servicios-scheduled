package com.mx.org.concentradora.model;

import java.security.Timestamp;
import java.util.Date;

public class RequestModel {

	private String tclave;
	private String caja;
	private Integer idTurno;
	private Integer idTicket;
	private Integer iClave;
	private Integer pclave;
	private String icb;
	private Integer folio;
	private Date fecha;
	private Integer estatus;
	private Timestamp atmFecha;
	private Integer userId;
	private Float monto;
	private String referencia;

	public RequestModel(String tclave, String caja, Integer idTurno, Integer idTicket, Integer iClave, Integer pclave,
			String icb, Integer folio, Date fecha, Integer estatus, Timestamp atmFecha, Integer userId, Float monto,
			String referencia) {
		super();
		this.tclave = tclave;
		this.caja = caja;
		this.idTurno = idTurno;
		this.idTicket = idTicket;
		this.iClave = iClave;
		this.pclave = pclave;
		this.icb = icb;
		this.folio = folio;
		this.fecha = fecha;
		this.estatus = estatus;
		this.atmFecha = atmFecha;
		this.userId = userId;
		this.monto = monto;
		this.referencia = referencia;
	}

	public RequestModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RequestModel [tclave=" + tclave + ", caja=" + caja + ", idTurno=" + idTurno + ", idTicket=" + idTicket
				+ ", iClave=" + iClave + ", pclave=" + pclave + ", icb=" + icb + ", folio=" + folio + ", fecha=" + fecha
				+ ", estatus=" + estatus + ", atmFecha=" + atmFecha + ", userId=" + userId + ", monto=" + monto
				+ ", referencia=" + referencia + "]";
	}

	public String getTclave() {
		return tclave;
	}

	public void setTclave(String tclave) {
		this.tclave = tclave;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public Integer getiClave() {
		return iClave;
	}

	public void setiClave(Integer iClave) {
		this.iClave = iClave;
	}

	public Integer getPclave() {
		return pclave;
	}

	public void setPclave(Integer pclave) {
		this.pclave = pclave;
	}

	public String getIcb() {
		return icb;
	}

	public void setIcb(String icb) {
		this.icb = icb;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Timestamp getAtmFecha() {
		return atmFecha;
	}

	public void setAtmFecha(Timestamp atmFecha) {
		this.atmFecha = atmFecha;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}
