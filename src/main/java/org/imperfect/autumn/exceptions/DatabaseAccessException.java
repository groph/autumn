package org.imperfect.autumn.exceptions;

public class DatabaseAccessException extends RuntimeException {
	
	public DatabaseAccessException() {
		
	}
	
	public DatabaseAccessException(String message) {
		super(message);
	}
	
	public DatabaseAccessException(Exception ex) {
		super(ex);
	}
	
}
