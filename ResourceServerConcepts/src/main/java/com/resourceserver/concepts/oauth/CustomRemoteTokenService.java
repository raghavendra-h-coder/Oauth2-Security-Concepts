package com.resourceserver.concepts.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class CustomRemoteTokenService extends RemoteTokenServices {
	protected final Log logger = LogFactory.getLog(getClass());
	private RestOperations restTemplate;

	private String checkTokenEndpointUrl;

	private String tokenName = "token";

	private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

	
	public CustomRemoteTokenService() {
		super();
		restTemplate = new RestTemplate();
		((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Ignore 400
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getRawStatusCode() != 400) {
					super.handleError(response);
				}
			}
		});
	}

	public String getCheckTokenEndpointUrl() {
		return checkTokenEndpointUrl;
	}

	@Override
	public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
		this.checkTokenEndpointUrl = checkTokenEndpointUrl;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) {
		logger.info("in load authentication module");
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add(tokenName, accessToken);
		logger.info("accesstoken:"+accessToken);
		HttpHeaders headers = new HttpHeaders();
		Map<String, Object> map = postToMap(checkTokenEndpointUrl, formData, headers);
		logger.info("map:"+map.toString());
		if (map.containsKey("error")) {
			logger.debug("check_token returned error: " + map.get("error"));
			throw new InvalidTokenException(accessToken);
		}

		Assert.state(map.containsKey("client_id"), "Client id must be present in response from auth server");
		return tokenConverter.extractAuthentication(map);
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> postToMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
		if (headers.getContentType() == null) {
			List<MediaType> media = new ArrayList<>();
			media.add(MediaType.APPLICATION_JSON);
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(media);
		}
		logger.info("post to map 1");
		logger.info("path:"+path);
		Map map = restTemplate.postForObject(path,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class);
		logger.info("post to map 2");
		@SuppressWarnings("unchecked")
		Map<String, Object> result = map;
		return result;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

}
