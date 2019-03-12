package com.example.error;

public class MovieNotFoundException extends RuntimeException{
	
	
	private String value;

	public MovieNotFoundException(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
