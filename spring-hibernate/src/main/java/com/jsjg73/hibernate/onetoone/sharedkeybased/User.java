package com.jsjg73.hibernate.onetoone.sharedkeybased;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String userName;
	
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)// user 엔티티가 저장될때 adress 엔티티도 함께 저장되도록함.
	@PrimaryKeyJoinColumn
	private Address address;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	

}
