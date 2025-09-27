package com.booklovers.myblogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     http
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/admin/**").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.POST, "/post/*/comments").hasAnyRole("USER", "ADMIN")
	                .requestMatchers(HttpMethod.GET, "/", "/posts", "/posts/**").permitAll()
	                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
	                .anyRequest().authenticated()
	                
	            )
	            .formLogin(formLogin -> formLogin
	                .loginPage("/login")
	                .permitAll()
	            )
	            .logout(logout -> logout
	                .permitAll()
	                .logoutSuccessUrl("/")
	            );

	        return http.build();
	    }
}
