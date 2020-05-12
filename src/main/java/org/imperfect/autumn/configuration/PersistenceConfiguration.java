package org.imperfect.autumn.configuration;

public class PersistenceConfiguration extends PersistenceProperties {
	
	public String getProvider() {
		return getProperty("javax.persistence.provider");
	}
	
	public String gtTransactionType() {
		return getProperty("javax.persistence.transactionType");
	}
	
	public String getUsername() {
		return getProperty("hibernate.connection.username");
	}
	
	public String getPassword() {
		return getProperty("hibernate.connection.password");
	}
	
	public String getDriver() {
		return getProperty("hibernate.connection.driver_class");
	}
	
	public String getUrl() {
		return getProperty("hibernate.connection.url");
	}
	
	public String getDialect() {
		return getProperty("hibernate.dialect");
	}
	
	public String getHbm2ddlAuto() {
		return getProperty("hibernate.hbm2ddl.auto");
	}
	
	public boolean isShowSql() {
		return Boolean.parseBoolean(getProperty("hibernate.show_sql"));
	}
	
	public boolean isFormatSql() {
		return Boolean.parseBoolean(getProperty("hibernate.format_sql"));
	}
	
}
