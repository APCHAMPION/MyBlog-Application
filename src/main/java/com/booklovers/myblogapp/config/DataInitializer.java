package com.booklovers.myblogapp.config;



import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.booklovers.myblogapp.model.User;
import com.booklovers.myblogapp.repository.UserRepository;

@Component
public class DataInitializer  implements CommandLineRunner{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * @param userRepository
	 * @param passwordEncoder
	 */
	public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		User adminUser = new User();
		
		adminUser.setUsername("admin");
		
		adminUser.setPassword(passwordEncoder.encode("password"));
		
		adminUser.setRole("ADMIN");
		
		userRepository.save(adminUser);
		
		System.out.println("Created default admin user with username 'admin' and password 'password'");
		
	}

}
