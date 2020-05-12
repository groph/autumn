package org.imperfect.autumn;

import com.google.inject.Injector;
import org.imperfect.autumn.configuration.BindingInstances;
import org.imperfect.autumn.user.UserContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Application implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		Injector injector = BindingInstances.injector();
		
		injector.getInstance(UserContext.class).initialize(context);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Intentionally empty.
	}
	
}
