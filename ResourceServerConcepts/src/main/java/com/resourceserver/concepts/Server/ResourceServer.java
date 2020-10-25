package com.resourceserver.concepts.Server;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@SpringBootApplication
@ComponentScan(value={"com.resourceserver.concepts.*"})
public class ResourceServer {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServer.class, args);
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/auth");
		datasource.setUsername("root");
		datasource.setPassword("root");
		return datasource;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource());
	}
}
