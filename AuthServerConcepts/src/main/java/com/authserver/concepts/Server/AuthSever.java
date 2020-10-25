package com.authserver.concepts.Server;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@SpringBootApplication
@EnableJpaRepositories("com.authserver.concepts.Repository")
@ComponentScan(value = { "com.authserver.concepts.Resource","com.authserver.concepts.Service","com.authserver.concepts.Config" })
public class AuthSever {

	public static void main(String[] args) {
		SpringApplication.run(AuthSever.class, args);
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource());
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
	
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("com.authserver.concepts.Entity");
		entityManagerFactoryBean.setPersistenceUnitName("portalentitymanager");
		return entityManagerFactoryBean;
	}

}
