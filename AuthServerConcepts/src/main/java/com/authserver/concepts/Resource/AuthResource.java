package com.authserver.concepts.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface AuthResource {
	
	@GetMapping("/user/details")
	ResponseEntity<String> getAllUsers();

}
