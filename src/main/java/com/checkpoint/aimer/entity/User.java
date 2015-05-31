package com.checkpoint.aimer.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.checkpoint.aimer.utils.RegistrationRequest;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;

@Entity
@Embeddable
public class User {

	@Id @GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private UserCredits credits;
	
	@Column(name="name",nullable=false)
	private String firstName;
	
	@Column(name="surname",nullable=false)
	private String secondName;
	
	@OneToMany(mappedBy="user", cascade= CascadeType.ALL)
	private List<Aim> aims;

	@OneToMany(mappedBy="user", cascade= CascadeType.ALL)
	private List<AimComment> comments;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="friend_1")
	private Set<ConfirmedFriendships> friendships;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="friend_2")
	private Set<UnconfirmedFriendships> requests;
	
	public User() {
		
	}
	
	public User(RegistrationRequest request) {
		this.firstName = request.getFirstName();
		this.secondName = request.getSecondName();
		this.credits = new UserCredits();
		this.credits.setLogin(request.getNickname());
		this.credits.setPassword(request.getPassword());
	}
	
	public User(User u) {
		this.aims = u.aims;
		this.firstName = u.firstName;
		this.comments = u.comments;
		this.secondName = u.secondName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCredits(UserCredits credits) {
		this.credits = credits;
	}
	
	@Embedded
	@JsonIgnore
	public UserCredits getCredits() {
		return this.credits;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	@JsonGetter("aims")
	public List<Aim> getAims() {
		return aims;
	}

	@JsonIgnore
	public void setAims(List<Aim> aims) {
		this.aims = aims;
	}

	public void addAim(Aim aim) {
		this.aims.add(aim);
	}

	@JsonGetter("comments")
	public List<AimComment> getComments() {
		return comments;
	}

	@JsonIgnore
	public void setComments(List<AimComment> comments) {
		this.comments = comments;
	}
	
	public void addFriendship(ConfirmedFriendships friendship){
		this.friendships.add(friendship);
	}
	
	public void addRequest(UnconfirmedFriendships unconfirmed) {
		this.requests.add(unconfirmed);
	}
	
	@JsonIgnore
	public Set<ConfirmedFriendships> getFriendship() {
		return this.friendships;
	}
	
	@JsonGetter("friends")
	public List<User> getFriendList(){
		List<User> friends = new ArrayList<User>();
		for(ConfirmedFriendships friendship: this.friendships)
			friends.add(friendship.friend_2);
		return friends;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if(obj instanceof User)
			return this.credits.getLogin().equals(((User)obj).getCredits().getLogin());
		else return false;
	}
}
