package com.datagrokr.todo.exceptions;

public class ErrorProps {
	
	private String status;
	private String errorMessage;
	
	public ErrorProps(){}
	
	public ErrorProps(String statusFromOutside, String errorMessageFromOutside)
	{
		this.status = statusFromOutside;
		this.errorMessage = errorMessageFromOutside;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	

}
