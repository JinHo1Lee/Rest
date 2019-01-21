package com.examples.request;

import javax.ws.rs.FormParam;

public class RestFulReqPost {
	@FormParam("message")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
