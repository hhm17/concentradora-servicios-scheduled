package com.mx.org.concentradora.entity;

public class Response {

	private int responseCode;
	private String responseMsg;

	public Response(int responseCode, String responseMsg) {
		super();
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}

	@Override
	public String toString() {
		return "Response [responseCode=" + responseCode + ", responseMsg=" + responseMsg + "]";
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

}
