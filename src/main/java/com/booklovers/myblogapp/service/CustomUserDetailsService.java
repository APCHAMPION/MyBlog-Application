package com.booklovers.myblogapp.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booklovers.myblogapp.model.User;
import com.booklovers.myblogapp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	/**
	 * @param userRepository
	 */
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with username: "+username));
	
		
		List<GrantedAuthority> authorities 
			= Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

     
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
		
		
		
	}
	
	
	
	
}
