package com.checkpoint.aimer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.checkpoint.aimer.security.AuthorizationRequestFilter;
import com.checkpoint.aimer.security.UserSecurityService;

@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetails() {
		return new UserSecurityService();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and()
			.authorizeRequests()
				.antMatchers("/aimer/**","/swagger/index.html").permitAll()
				.antMatchers("/auth/**").anonymous()
				.antMatchers("/**").hasRole("USER")
				.and()
			.csrf().disable();
		
		http.addFilterBefore(filter(), BasicAuthenticationFilter.class);
	}
	
	@Bean
	public AuthorizationRequestFilter filter() {
		AuthorizationRequestFilter authFilter = new AuthorizationRequestFilter("/login");
		authFilter.setUserDetatilsService(userDetails());
		return authFilter;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(userDetails());
	}
}