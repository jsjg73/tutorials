package com.jsjg73.hibernate.onetoone.jointablebased;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class WorkStation {
	@Id
	@GeneratedValue
	@Column(name="ws_id")
	private Long id;
	@Column
	private Integer workStationNumber;
	@Column
	private String floor;
	
	@OneToOne(mappedBy = "workStation")
	private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWorkStationNumber() {
		return workStationNumber;
	}

	public void setWorkStationNumber(Integer workStationNumber) {
		this.workStationNumber = workStationNumber;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
}
