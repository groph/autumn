package org.imperfect.autumn.exceptions;

public class DataAccessException extends RuntimeException {
	
	public DataAccessException(String message) {
		super(message);
	}
	
	public DataAccessException(Exception ex) {
		super(ex);
	}
	
}
