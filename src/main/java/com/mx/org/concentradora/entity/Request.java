package com.mx.org.concentradora.entity;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

//@Entity
//@Table(name = "requests")
public class Request implements Serializable {

	private static final long serialVersionUID = -1942871886672089608L;

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(name = "tclave")
	private String tclave;

	// @Column(name = "caja")
	private String caja;

	// @Column(name = "idturno")
	private int idturno;

	// @Column(name = "idticket")
	private int idticket;

	// @Column(name = "iclave")
	private int iclave;

	// @Column(name = "pclave")
	private int pclave;

	// @Column(name = "icb")
	private String icb;

	// @Column(name = "folio")
	private int folio;

	// @Column(name = "fecha")
	private Date fecha;

	// @Column(name = "estatus")
	private int estatus;

	// @Column(name = "atmfecha")
	private Timestamp atmfecha;

	// @Column(name = "userid")
	private int userid;

	// @Column(name = "monto")
	private Float monto;

	// @Column(name = "referencia")
	private String referencia;

	public Request() {

	}

	public Request(String tclave, String caja, int idturno, int idticket, int iclave, int pclave, String icb, int folio,
			Date fecha, int estatus, Timestamp atmfecha, int userid, Float monto, String referencia) {
		super();
		this.tclave = tclave;
		this.caja = caja;
		this.idturno = idturno;
		this.idticket = idticket;
		this.iclave = iclave;
		this.pclave = pclave;
		this.icb = icb;
		this.folio = folio;
		this.fecha = fecha;
		this.estatus = estatus;
		this.atmfecha = atmfecha;
		this.userid = userid;
		this.monto = monto;
		this.referencia = referencia;
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

	public int getIdturno() {
		return idturno;
	}

	public void setIdturno(int idturno) {
		this.idturno = idturno;
	}

	public int getIdticket() {
		return idticket;
	}

	public void setIdticket(int idticket) {
		this.idticket = idticket;
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

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
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

	public Timestamp getAtmFecha() {
		return atmfecha;
	}

	public void setAtmFecha(Timestamp atmfecha) {
		this.atmfecha = atmfecha;
	}

	public int getUserId() {
		return userid;
	}

	public void setUserId(int userid) {
		this.userid = userid;
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
