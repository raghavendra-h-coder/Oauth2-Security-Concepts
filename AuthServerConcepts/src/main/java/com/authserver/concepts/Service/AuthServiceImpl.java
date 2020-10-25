package com.authserver.concepts.Service;

import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public String getUserDetails() {
		return "Hello All Users";
	}

}
