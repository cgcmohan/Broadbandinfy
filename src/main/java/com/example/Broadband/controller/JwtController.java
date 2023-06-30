package com.example.Broadband.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Broadband.Model.AuthRequest;
import com.example.Broadband.Model.AuthResponse;
import com.example.Broadband.Security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class JwtController {

	@Autowired
	JwtHelper jwtHelper;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	Logger log = LoggerFactory.getLogger(JwtController.class);
	
	@PostMapping(value="/current-user")
	public String currentUser(Principal principal) {
		return principal.getName();
	}
	@PostMapping(value="/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		this.doAuthenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtHelper.generateToken(userDetails);
		
		AuthResponse response = new AuthResponse();
		response.setJwtToken(token);
		response.setUsername(userDetails.getUsername());
								
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid username or password !!");
		}
		
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Invalid Credential..!";
	}
}
