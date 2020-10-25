package com.resourceserver.concepts.oauth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
@Order(6)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("rest_api");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new OAuthRequestedMatcher()).authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.and()
		.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

	private static class OAuthRequestedMatcher implements RequestMatcher {
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader(Constant.AUTHORIZATION_HEADER);
			// Determine if the client request contained an OAuth Authorization
			boolean haveOauth2Token = (auth != null) && auth.startsWith(Constant.BEARER_TOKEN_TYPE);
			boolean haveAccessToken = request.getParameter(Constant.ACCESS_TOKEN) != null;
			return haveOauth2Token || haveAccessToken;
		}
	}

	@Bean
	public DefaultTokenServices tokenService() {
		DefaultTokenServices tokenService = new DefaultTokenServices();
		tokenService.setTokenStore(tokenStore);
		return tokenService;
	}
	
	@Bean
	@Primary
	public CustomRemoteTokenService remoteTokenService() {
		final CustomRemoteTokenService remoteTokenServices = new CustomRemoteTokenService();
		remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8082/oauth/check_token");
		return remoteTokenServices;
	}
	
	public static class Constant{
		Constant(){
			
		}
		private static final String AUTHORIZATION_HEADER = "Authorization";
		private static final String BEARER_TOKEN_TYPE = "Bearer";
		private static final String ACCESS_TOKEN = "access_token";
		
	}
}
