package com.authserver.concepts.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authserver.concepts.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUsername(String username);

}
