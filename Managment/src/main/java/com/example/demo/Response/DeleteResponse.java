package com.example.demo.Response;

public class DeleteResponse {

	private String message;
	private String color;

	public DeleteResponse() {
		super();
	}

	public DeleteResponse(String message, String color) {
		super();
		this.message = message;
		this.color = color;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "DeleteResponse [message=" + message + ", color=" + color + "]";
	}

}
