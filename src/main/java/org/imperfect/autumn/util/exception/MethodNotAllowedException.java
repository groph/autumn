package org.imperfect.autumn.util.exception;

import javax.servlet.ServletException;

public class MethodNotAllowedException extends ServletException {
	
	public MethodNotAllowedException() {
		super("Method not allowed!");
	}
	
}
