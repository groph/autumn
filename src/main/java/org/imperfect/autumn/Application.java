package org.imperfect.autumn;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.imperfect.autumn.controllers.UserController;
import org.imperfect.autumn.filters.CommonRestHeadersFilter;
import org.imperfect.autumn.filters.ExceptionHandlerFilter;

@WebListener
public class Application implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		Injector injector = BindingInstances.injector();
		
		context.addServlet("userController", injector.getInstance(UserController.class))
				.addMapping("/users/*");
		
		context.addFilter("commonRestHeadersFilter", new CommonRestHeadersFilter())
				.addMappingForUrlPatterns(null, false, "/*");
		
		context.addFilter("guiceFilter", new GuiceFilter())
				.addMappingForUrlPatterns(null, false, "/*");
		
		context.addFilter("exceptionHandlerFilter", new ExceptionHandlerFilter())
				.addMappingForUrlPatterns(null, false, "/*");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}
