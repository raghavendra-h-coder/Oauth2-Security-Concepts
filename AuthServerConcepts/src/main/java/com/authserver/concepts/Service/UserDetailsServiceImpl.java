package com.authserver.concepts.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authserver.concepts.Config.UserPrincipal;
import com.authserver.concepts.Entity.User;
import com.authserver.concepts.Repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUsername(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Not found saar");
		}
		return new UserPrincipal(user);
	}

}
