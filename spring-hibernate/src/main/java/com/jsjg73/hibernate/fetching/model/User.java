package com.jsjg73.hibernate.fetching.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.engine.internal.Collections;

@Entity
@Table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserDetail> details = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<UserFamily> family = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<UserDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<UserDetail> details) {
		this.details = details;
	}

	public Set<UserFamily> getFamily() {
		return family;
	}

	public void setFamily(Set<UserFamily> family) {
		this.family = family;
	}

	public void appendOrders(UserDetail... userDetails) {
		details.addAll(Arrays.stream(userDetails).collect(Collectors.toList()));
	}

	public void appendFamily(UserFamily... userFamilies) {
		family.addAll(Arrays.stream(userFamilies).collect(Collectors.toList()));		
	}
	
	
}
