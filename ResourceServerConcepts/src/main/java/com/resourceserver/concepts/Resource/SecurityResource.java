package com.resourceserver.concepts.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface SecurityResource {
	
	@RequestMapping(value="/api/secure/items",method=RequestMethod.GET)
	ResponseEntity<String> getItems();
	
	@RequestMapping(value="/api/insecure/items",method=RequestMethod.GET)
	ResponseEntity<String> getInsecureItems();

}
