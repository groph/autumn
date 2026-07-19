package org.imperfect.autumn.shared.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CommonRestHeadersFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) {
		// Intentionally empty.
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		// Intentionally empty.
	}
	
}
