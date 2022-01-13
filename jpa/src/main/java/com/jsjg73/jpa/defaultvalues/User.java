package com.jsjg73.jpa.defaultvalues;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
public class User {
	@Id
	private Long id;
	
	@Column(columnDefinition = "varchar(255) default 'John Snow'")
	private String firstName;
	
	@Column(columnDefinition = "integer default 25")
	private Integer age ;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean locked ;
	
	public User() {
	}
	
	public User(String firstName, Integer age, Boolean locked) {
		super();
		this.firstName = firstName;
		this.age = age;
		this.locked = locked;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
}
