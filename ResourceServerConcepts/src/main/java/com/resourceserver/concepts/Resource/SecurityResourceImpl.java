package com.resourceserver.concepts.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.resourceserver.concepts.Service.SecurityService;

@RestController
public class SecurityResourceImpl implements SecurityResource {
	
	@Autowired
	private SecurityService securityService;

	public ResponseEntity<String> getItems() {
		return ResponseEntity.ok(securityService.getItems());
	}

	@Override
	public ResponseEntity<String> getInsecureItems() {
		return ResponseEntity.ok(securityService.getInsecuredItems());
	}

}
