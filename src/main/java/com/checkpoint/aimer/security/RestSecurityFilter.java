package com.checkpoint.aimer.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class RestSecurityFilter extends AbstractAuthenticationProcessingFilter{
	
	public RestSecurityFilter(String url, AuthenticationManager m) {
		super(url);
		this.setAuthenticationManager(m);
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		Cookie cookies[] = request.getCookies();
		if(cookies!=null)for(Cookie cookie : cookies)
			System.out.println(cookie);
		Authentication auth = this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken("roman", "sawawluha"));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}
	
	@Override 
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication auth){
		System.out.println("sccess");
	}
	
}
