package org.imperfect.autumn.shared.filter;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.ws.rs.core.MediaType;
import org.imperfect.autumn.util.exception.MethodNotAllowedException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ExceptionHandlerFilter implements Filter {
	
	private final ObjectMapper mapper;

	public ExceptionHandlerFilter(ObjectMapper mapper) {
		this.mapper = mapper;
	}

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
