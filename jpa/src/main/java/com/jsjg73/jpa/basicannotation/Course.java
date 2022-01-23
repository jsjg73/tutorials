package com.jsjg73.jpa.basicannotation;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class Course {
	
	@Id
	private long id;
	
	@Basic(optional = false, fetch = FetchType.LAZY)
	private String notNullByBasic;

	@Column(columnDefinition = "varchar(255) not null")
	private String notNullByDB;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNotNullByBasic() {
		return notNullByBasic;
	}

	public void setNotNullByBasic(String notNullByBasic) {
		this.notNullByBasic = notNullByBasic;
	}

	public String getNotNullByDB() {
		return notNullByDB;
	}

	public void setNotNullByDB(String notNullByDB) {
		this.notNullByDB = notNullByDB;
	}

}
