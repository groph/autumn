package org.imperfect.autumn;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.imperfect.autumn.adapters.UserAdapter;
import org.imperfect.autumn.controllers.UserController;
import org.imperfect.autumn.services.UserService;

public class BindingInstances extends AbstractModule {
	
	public static final URL PROPERTIES_PATH = BindingInstances.class
			.getResource("/Application.yml");
	
	public static Injector injector() {
		return Guice.createInjector(new BindingInstances());
	}
	
	@Override
	protected void configure() {
		// Intentionally empty.
	}
	
	@Provides
	public Configuration configuration() throws IOException {
		Configuration properties = new Configuration();
		properties.loadFromYML(PROPERTIES_PATH.openStream());
		return properties;
	}
	
	@Provides
	public Connection connection(Configuration configuration)
			throws ClassNotFoundException, SQLException {
		String driver = configuration.getDriver();
		Class.forName(driver);
		
		String url = configuration.getUrl();
		String username = configuration.getUsername();
		String password = configuration.getPassword();
		
		return DriverManager.getConnection(url, username, password);
	}
	
	@Provides
	public UserAdapter userAdapter(Connection connection) {
		return new UserAdapter(connection);
	}
	
	@Provides
	public UserService userService(UserAdapter userAdapter) {
		return new UserService(userAdapter);
	}
	
	@Provides
	public UserController userService(UserService userService) {
		return new UserController(userService);
	}
	
}
