package com.authserver.concepts.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.authserver.concepts.Service.UserDetailsServiceImpl;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private TokenStore tokenStore;
//	@Autowired
//	private UserDetailsServiceImpl userDetailsService;
//	@Autowired
//	private UserApprovalHandler userApprovalHandler;
	@Autowired
	private AuthenticationManager authenticationManager;
//	@Autowired
//	private ClientDetailsService clientDetailsService;


	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

//	@Bean
//	@Primary
//	public DefaultTokenServices tokenServices() {
//		DefaultTokenServices tokenServices = new DefaultTokenServices();
//		tokenServices.setSupportRefreshToken(true);
//		tokenServices.setAccessTokenValiditySeconds(3600);
//		tokenServices.setRefreshTokenValiditySeconds(3600);
//		tokenServices.setTokenStore(new JdbcTokenStore(dataSource));
//		return tokenServices;
//	}

//	@Bean
//	@Autowired
//	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
//		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//		handler.setTokenStore(tokenStore);
//		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//		handler.setClientDetailsService(clientDetailsService);
//		return handler;
//	}
//
//	@Bean
//	@Autowired
//	public ApprovalStore approvalStore(TokenStore tokenStore) {
//		TokenApprovalStore store = new TokenApprovalStore();
//		store.setTokenStore(tokenStore);
//		return store;
//	}

}
