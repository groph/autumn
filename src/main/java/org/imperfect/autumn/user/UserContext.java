package org.imperfect.autumn.user;

import com.google.inject.Inject;
import com.google.inject.servlet.GuiceFilter;
import org.imperfect.autumn.shared.filter.CommonRestHeadersFilter;
import org.imperfect.autumn.shared.filter.ExceptionHandlerFilter;

import javax.servlet.ServletContext;

public class UserContext {
	
	private final UserController controller;
	private final CommonRestHeadersFilter commonRestHeadersFilter;
	private final GuiceFilter guiceFilter;
	private final ExceptionHandlerFilter exceptionHandlerFilter;
	
	@Inject
	public UserContext(
			UserController controller,
			CommonRestHeadersFilter commonRestHeadersFilter,
			GuiceFilter guiceFilter,
			ExceptionHandlerFilter exceptionHandlerFilter) {
		
		this.controller = controller;
		this.commonRestHeadersFilter = commonRestHeadersFilter;
		this.guiceFilter = guiceFilter;
		this.exceptionHandlerFilter = exceptionHandlerFilter;
	}
	
	public void initialize(ServletContext context) {
		context.addServlet("userController", controller)
				.addMapping("/users/*");
		
		context.addFilter("commonRestHeadersFilter", commonRestHeadersFilter)
				.addMappingForUrlPatterns(null, false, "/*");
		
		context.addFilter("guiceFilter", guiceFilter)
				.addMappingForUrlPatterns(null, false, "/*");
		
		context.addFilter("exceptionHandlerFilter", exceptionHandlerFilter)
				.addMappingForUrlPatterns(null, false, "/*");
	}
	
}
