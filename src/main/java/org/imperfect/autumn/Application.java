package org.imperfect.autumn;

import com.google.inject.Injector;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.imperfect.autumn.configuration.BindingInstances;
import org.imperfect.autumn.user.UserContext;

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
