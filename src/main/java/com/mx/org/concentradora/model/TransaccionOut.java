package com.mx.org.concentradora.model;

import java.util.Date;

public class TransaccionOut {

	private Long id;
	private String tclave;
	private String caja;
	private int idTurno;
	private int idTicket;
	private int iclave;
	private int pclave;
	private String icb;
	private Date fecha;
	private int estatus;
	private Date atmFecha;
	private int userId;
	private Double monto;
	private String referencia;
	private String folio;
	private String folioProv;
	private String respProv;
	private String leyendaTck;
	private Date fechaResp;
	private String canalVenta;

	public TransaccionOut() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public int getIclave() {
		return iclave;
	}

	public void setIclave(int iclave) {
		this.iclave = iclave;
	}

	public int getPclave() {
		return pclave;
	}

	public void setPclave(int pclave) {
		this.pclave = pclave;
	}

	public String getIcb() {
		return icb;
	}

	public void setIcb(String icb) {
		this.icb = icb;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Date getAtmFecha() {
		return atmFecha;
	}

	public void setAtmFecha(Date atmFecha) {
		this.atmFecha = atmFecha;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFolioProv() {
		return folioProv;
	}

	public void setFolioProv(String folioProv) {
		this.folioProv = folioProv;
	}

	public String getRespProv() {
		return respProv;
	}

	public void setRespProv(String respProv) {
		this.respProv = respProv;
	}

	public String getLeyendaTck() {
		return leyendaTck;
	}

	public void setLeyendaTck(String leyendaTck) {
		this.leyendaTck = leyendaTck;
	}

	public Date getFechaResp() {
		return fechaResp;
	}

	public void setFechaResp(Date fechaResp) {
		this.fechaResp = fechaResp;
	}

	public String getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(String canalVenta) {
		this.canalVenta = canalVenta;
	}

}
