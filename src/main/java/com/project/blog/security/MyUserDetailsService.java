package com.project.blog.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	
	UserRepository userRepo;
	
	
	@Autowired
	public MyUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}



	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		User user = userRepo.findByUsername(userName);
		if(user==null) {
			user = userRepo.findByEmail(userName);
		}
		return JwtUserDetails.create(user);
	}
	
	public UserDetails loadUserByEmail(String eMail) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(eMail);
		return JwtUserDetails.create(user);
	}
	
	public UserDetails loadUserById(long id) {
		
		User user = userRepo.findById(id).get();
		
		return JwtUserDetails.create(user);
	}

}
