package com.jsjg73.hibernate.fetching.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserFamily {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result = 31*result + ( (id==null)? 0: id.hashCode() );
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(this == obj)return true;
		UserFamily other = (UserFamily) obj;
		
		if(this.id == null) {
			if(other.id != null)
				return false;
		}else if(!other.id.equals(this.id)){
			return false;
		}
		return true;
	}
}
