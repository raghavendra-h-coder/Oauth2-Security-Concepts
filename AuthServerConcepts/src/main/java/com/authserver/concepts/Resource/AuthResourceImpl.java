package com.authserver.concepts.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.authserver.concepts.Service.AuthService;

@RestController
public class AuthResourceImpl implements AuthResource {
	
	@Autowired
	private AuthService authService;

	@Override
	public ResponseEntity<String> getAllUsers() {
		return ResponseEntity.ok(authService.getUserDetails());
	}

}
