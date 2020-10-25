package com.resourceserver.concepts.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class OAuth2FeignRequestInterceptor {

	@Bean
	public RequestInterceptor requestTokenBearerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				System.out.println("1");
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				System.out.println("2");
				if (authentication != null) {
					System.out.println("3");
					OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
					System.out.println("4");
					requestTemplate.header(Constant.AUTHORIZATION_HEADER,
							Constant.BEARER_TOKEN_TYPE + details.getTokenValue());
					System.out.println("5");
				}
			}
		};
	}
	
	public static class Constant{
		Constant(){
			
		}
		private static final String AUTHORIZATION_HEADER = "Authorization";
		private static final String BEARER_TOKEN_TYPE = "Bearer";
		
	}

}
