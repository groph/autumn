package org.imperfect.autumn.shared.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imperfect.autumn.util.exception.MethodNotAllowedException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.persistence.EntityNotFoundException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.core.MediaType;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ExceptionHandlerFilter implements Filter {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void init(FilterConfig filterConfig) {
		// Intentionally empty.
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch(IllegalArgumentException ex) {
			HttpServletResponse errorResponse = new HttpServletResponseWrapper(
					(HttpServletResponse) response);
			handleErrors(errorResponse, ex.getMessage(), SC_BAD_REQUEST);
		} catch(EntityNotFoundException ex) {
			HttpServletResponse errorResponse = new HttpServletResponseWrapper(
					(HttpServletResponse) response);
			handleErrors(errorResponse, ex.getMessage(), SC_NOT_FOUND);
		} catch(MethodNotAllowedException ex) {
			HttpServletResponse errorResponse = new HttpServletResponseWrapper(
					(HttpServletResponse) response);
			handleErrors(errorResponse, ex.getMessage(), SC_METHOD_NOT_ALLOWED);
		}
	}
	private void handleErrors(HttpServletResponse response, String message, int statusCode)
			throws IOException {
		
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(statusCode);
		
		response.getWriter().write(mapper.writeValueAsString(message));
	}
	
	@Override
	public void destroy() {
		// Intentionally empty.
	}
	
}
