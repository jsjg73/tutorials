package java.com.jsjg73.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "STUDENT", schema = "SCHOOL")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="STUDENT_NAME", length = 50, nullable = false, unique = false)
	private String name;
	
	@Transient
	private Integer age;
	
	@Temporal(TemporalType.DATE)
	private Date birthData;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getAge() {
		return age;
	}

	public Date getBirthData() {
		return birthData;
	}

	public void setBirthData(Date birthData) {
		this.birthData = birthData;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
}
