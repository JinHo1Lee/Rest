package com.examples.request;


import javax.ws.rs.QueryParam;


public class RestFulReq {
	@QueryParam("message")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
