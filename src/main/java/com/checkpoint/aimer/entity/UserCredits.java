package com.checkpoint.aimer.entity;

import javax.persistence.Embeddable;

@Embeddable
public class UserCredits {

	private String login;
	
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
