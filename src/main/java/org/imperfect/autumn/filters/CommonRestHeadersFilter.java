package org.imperfect.autumn.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.core.MediaType;

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
