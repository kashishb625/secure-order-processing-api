package com.Kashish.secure_order_api.dto;

public class PasswordUpdateResponse 
{
	private int status;
	private String message;
	
	public PasswordUpdateResponse(int status, String message) 
	{
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}
}
