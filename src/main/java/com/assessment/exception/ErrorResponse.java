package com.assessment.exception;

public class ErrorResponse {
	private String error;
	private String errorMessage;
	private String timeStamp;
	
	public ErrorResponse(String error, String errorMessage, String timeStamp) {
		super();
		this.error = error;
		this.errorMessage = errorMessage;
		this.timeStamp = timeStamp;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
