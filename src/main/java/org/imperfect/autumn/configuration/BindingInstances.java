package org.imperfect.autumn.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceFilter;
import org.imperfect.autumn.shared.filter.CommonRestHeadersFilter;
import org.imperfect.autumn.shared.filter.ExceptionHandlerFilter;
import org.imperfect.autumn.user.UserContext;
import org.imperfect.autumn.user.UserRepository;
import org.imperfect.autumn.user.UserController;
import org.imperfect.autumn.user.UserService;

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
	@Singleton
	public PersistenceConfiguration configuration() throws IOException {
		PersistenceConfiguration properties = new PersistenceConfiguration();
		properties.loadFromYML(PROPERTIES_PATH.openStream());
		return properties;
	}
	
	@Provides
	@Singleton
	public Connection connection(PersistenceConfiguration persistenceConfiguration)
			throws ClassNotFoundException, SQLException {
		String driver = persistenceConfiguration.getDriver();
		Class.forName(driver);
		
		String url = persistenceConfiguration.getUrl();
		String username = persistenceConfiguration.getUsername();
		String password = persistenceConfiguration.getPassword();
		
		return DriverManager.getConnection(url, username, password);
	}
	
	@Provides
	@Singleton
	public UserRepository userAdapter(Connection connection) {
		return new UserRepository(connection);
	}
	
	@Provides
	@Singleton
	public UserService userService(UserRepository userRepository) {
		return new UserService(userRepository);
	}
	
	@Provides
	@Singleton
	public UserController userController(UserService userService, ObjectMapper objectMapper) {
		return new UserController(userService, objectMapper);
	}
	
	@Provides
	@Singleton
	public CommonRestHeadersFilter commonRestHeadersFilter() {
		return new CommonRestHeadersFilter();
	}
	@Provides
	@Singleton
	public GuiceFilter guiceFilter() {
		return new GuiceFilter();
	}
	
	@Provides
	@Singleton
	public ExceptionHandlerFilter exceptionHandlerFilter() {
		return new ExceptionHandlerFilter();
	}
	
	@Provides
	@Singleton
	public UserContext userContext(UserController userController) {
		return new UserContext(
				userController,
				commonRestHeadersFilter(),
				guiceFilter(),
				exceptionHandlerFilter());
	}
	
	@Provides
	@Singleton
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
}
