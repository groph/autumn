package org.imperfect.autumn.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.core.MediaType;

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
			handle4xxErrors(errorResponse, ex);
		}
	}
	
	private void handle4xxErrors(HttpServletResponse response, IllegalArgumentException ex)
			throws IOException {
		
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
		String message = String.format("\"%s\"", ex.getMessage());
		response.getWriter().write(mapper.writeValueAsString(message));
	}
	
	@Override
	public void destroy() {
		// Intentionally empty.
	}
	
}
