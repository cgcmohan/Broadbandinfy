package com.example.Broadband.Security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestHeader = request.getHeader("Authorization");
		log.info("Header {} : ", requestHeader);
		String userName = null;
		String token = null;
		if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			try {
				userName = jwtHelper.getUserNameFromToken(token);
			}
			catch(IllegalArgumentException e) {
				log.info("Illegal argument while fetching the username !!");
				e.printStackTrace();
				
			}
			catch(ExpiredJwtException e) {
				log.info("Given JWT token is expired !!");
				e.printStackTrace();
			}
			catch(MalformedJwtException e) {
				log.info("Some change has been done in token... Invalid token !!");
				e.printStackTrace();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			log.info("Invalid header!!");
		}
		
		if(userName !=null && SecurityContextHolder.getContext().getAuthentication() ==null ) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			Boolean isTokenValidated = this.jwtHelper.validateToken(token, userDetails);
			if(isTokenValidated) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			else {
				log.info("Validation fails !!");
			}
		}

		filterChain.doFilter(request, response);
	}
	
	
}
