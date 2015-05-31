package com.checkpoint.aimer.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.security.SecurityUtil;
import org.apache.sshd.common.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.entity.UserCredits;
import com.checkpoint.aimer.services.UserService;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = this.userService.getUserByNickname(username);
		
		if(user!=null) {
			System.out.println("returning user details");
			return new UserDetailsImpl(user); 
		} else 
			throw new UsernameNotFoundException("");
	}
	
	public static class UserDetailsImpl implements UserDetails {
		private UserCredits details;
		private User user;
		public UserDetailsImpl(User user) {
			this.details = user.getCredits();
			this.user = user;
		}
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
	        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	        System.out.println("_USER");
			return authorities;
		}
		
		public User getUser() {
			return this.user;
		}

		@Override
		public String getPassword() {
			return details.getPassword();
		}

		@Override
		public String getUsername() {
			return details.getLogin();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
}
