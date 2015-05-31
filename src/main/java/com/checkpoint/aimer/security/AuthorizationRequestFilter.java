package com.checkpoint.aimer.security;

import java.io.BufferedReader;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

/**
 * My own implementation of authorization filter. It simply consumes JSON-like
 * authorization data:
 * 
 * {
 * 	"login":"<user login>",
 *  "password":"<user password>"
 * }
 * 
 * it maps request that has processingURL, parses data, encode credits as base64
 * for basic authentication. It attaches header 'Authorization: Basic <encoded credits>', 
 * where <encoded credits> is a kind of string such "<login>:<password>", encoded in 
 * base64. You can further just append this header to any request that seem to be authorized
 * 
 * @author roman
 * 
 */
public class AuthorizationRequestFilter extends  GenericFilterBean{
	private static Logger logger = Logger.getLogger(AuthorizationRequestFilter.class);
	
	private String processingURL;
	private UserDetailsService service;
	
	public void setUserDetatilsService(UserDetailsService service) {
		this.service = service;
	}
	
	public AuthorizationRequestFilter(String url) {
		this.processingURL = url;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(((HttpServletRequest)request).getHeader("Authorization")==null && 
				((HttpServletRequest)request).getRequestURI().equals(processingURL)) {

			BufferedReader reader = request.getReader();
			JsonObject json = Json.createReader(reader).readObject();
			
			try {
				String toEncode = json.getString("login")+":"+json.getString("password");
				String coded = "Basic "+Base64.encodeBase64String(toEncode.getBytes());
				logger.log(Level.INFO, "Encoding "+toEncode+" - "+coded);
		
				((HttpServletResponse)response).addHeader("Authorization", coded);
				this.service.loadUserByUsername(json.getString("login"));
			} catch(UsernameNotFoundException e) {
				logger.log(Level.ERROR, json.getString("login") + " is not registred");
			}
		} else chain.doFilter(request, response);
	}
}
