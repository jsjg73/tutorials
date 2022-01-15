package com.jsjg73.hibernate.multiplicity.onetoone.foreignkeybased;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String userName;
	
	
	@OneToOne(cascade = CascadeType.ALL)// user ��ƼƼ�� ����ɶ� adress ��ƼƼ�� �Բ� ����ǵ�����.
	@JoinColumn(name="address_id", referencedColumnName = "add_id")
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
