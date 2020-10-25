package com.resourceserver.concepts.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SecurityServiceImpl implements SecurityService {

	public String getItems() {
		return "secured";
	}

	@Override
	public String getInsecuredItems() {
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
		  = "http://localhost:8082/oauth/check_token?token=f04741cb-9be8-405f-8e84-0e7338e79bc3";
		ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl, String.class);
		System.out.println("response:"+response);
		return "in secured";
	}

}
